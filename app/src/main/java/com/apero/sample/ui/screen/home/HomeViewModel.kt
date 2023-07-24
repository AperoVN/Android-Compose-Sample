package com.apero.sample.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.request.MoviePopularRequest
import com.apero.sample.data.repository.movie.IMovieRepository
import com.apero.sample.data.state.FailureState
import com.apero.sample.data.state.PagingData
import com.apero.sample.data.state.PagingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by KO Huyn on 20/07/2023.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("getListMoviePopular","from VM")
        getListMoviePopular()
    }

    fun getListMoviePopular(page: Int? = uiState.value.pagingMovie.page) {
        viewModelScope.launch {
            _uiState.update { it.copy(pagingMovie = it.createLoading(page)) }

            movieRepository.getListMoviePopular(MoviePopularRequest(page))
                .onSuccess { paging -> _uiState.update { it.copy(listMovie = it.listMovie + paging.list) } }
                .getOrElse { failureState ->
                    uiState.value.pagingMovie.copy(
                        pagingState = PagingState.createError(page, failureState)
                    )
                }.let { paging ->
                    _uiState.update { it.copy(pagingMovie = paging) }
                }
        }
    }

    fun loadMore() {
        val page = uiState.value.pagingMovie.page
        if (page != null) {
            getListMoviePopular(page + 1)
        }
    }
}

data class HomeUiState(
    val listMovie: List<Movie> = listOf(),
    val pagingMovie: PagingData<Movie> = PagingData(),
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