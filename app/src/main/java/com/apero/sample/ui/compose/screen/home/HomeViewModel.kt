package com.apero.sample.ui.compose.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.monitor.NetworkMonitor
import com.apero.sample.data.network.request.MoviePopularRequest
import com.apero.sample.data.remoteconfig.RemoteConfigRepository
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.data.repository.movie.IMovieRepository
import com.apero.sample.data.state.FailureState
import com.apero.sample.data.state.PagingData
import com.apero.sample.data.state.PagingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by KO Huyn on 20/07/2023.
 */

class HomeViewModel(
    private val movieRepository: IMovieRepository,
    private val monitor: NetworkMonitor,
    private val commonRepository: ICommonRepository,
    private val remoteConfig: RemoteConfigRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getListMoviePopular()
        checkAvailableNetwork()
        checkRefreshWhenLanguageChange()
        setSpanCountFromRemoteConfig()
    }


    fun loadMore() {
        val page = uiState.value.pagingMovie.page
        if (page != null) {
            getListMoviePopular(page + 1)
        }
    }

    fun refresh() {
        getListMoviePopular(null)
    }

    private fun getListMoviePopular(page: Int? = uiState.value.pagingMovie.page) {
        viewModelScope.launch {
            _uiState.update { it.copy(pagingMovie = it.createLoading(page)) }

            movieRepository.getListMoviePopular(MoviePopularRequest(page))
                .onRight { paging ->
                    _uiState.update {
                        if (paging.page == null || paging.page == 1) {
                            it.copy(listMovie = paging.list)
                        } else {
                            it.copy(listMovie = it.listMovie + paging.list)
                        }
                    }
                }
                .getOrElse { failureState ->
                    uiState.value.pagingMovie.copy(
                        pagingState = PagingState.createError(page, TODO("convert CallError to FailureState"))
                    )
                }.let { paging ->
                    _uiState.update { it.copy(pagingMovie = paging) }
                }
        }
    }

    private fun checkAvailableNetwork() {
        viewModelScope.launch {
            monitor.isOnline.distinctUntilChanged()
                .filter { uiState.value.pagingMovie.pagingState is PagingState.Failure }
                .onEach { Log.d("HomeViewModel", "monitor.isOnline:$it") }
                .collect {
                    getListMoviePopular()
                }
        }
    }

    private fun checkRefreshWhenLanguageChange() {
        viewModelScope.launch {
            val currentLanguage = commonRepository.getLanguage().firstOrNull()
            commonRepository.getLanguage()
                .filter { currentLanguage != it }
                .distinctUntilChanged()
                .onEach { Log.d("HomeViewModel", "commonRepository.getLanguage:$it") }
                .collect {
                    getListMoviePopular(page = null)
                }
        }
    }

    private fun setSpanCountFromRemoteConfig() {
        viewModelScope.launch {
            remoteConfig.getSpanCountHome()
                .filterNotNull()
                .distinctUntilChanged()
                .collect { spanCount ->
                    _uiState.update { it.copy(spanCount = spanCount) }
                }
        }
    }
}

data class HomeUiState(
    val listMovie: List<Movie> = listOf(),
    val pagingMovie: PagingData<Movie> = PagingData(),
    val spanCount: Int = 3
) {

    fun createLoading(page: Int?): PagingData<Movie> {
        return pagingMovie.copy(pagingState = PagingState.createLoading(page))
    }

    fun createError(page: Int?, failureState: FailureState): PagingData<Movie> {
        return pagingMovie.copy(pagingState = PagingState.createError(page, failureState))
    }
    companion object {
        fun mock() = HomeUiState(listMovie = List(10) { Movie.mock() })
    }
}