package com.apero.sample.data.repository.movie

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.data.converter.api.MovieApiToUiConverter
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.ApiService
import com.apero.sample.data.network.request.MoviePopularRequest
import com.apero.sample.data.repository.logMovieApi
import com.apero.sample.data.state.PagingData
import com.apero.sample.data.state.PagingState
import com.apero.sample.data.state.ResultState

/**
 * Created by KO Huyn on 20/07/2023.
 */
class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val analyticsHelper: AnalyticsHelper
) : IMovieRepository {
    override suspend fun getListMoviePopular(request: MoviePopularRequest): Either<CallError, PagingData<Movie>> {
        return apiService.getMoviePopular(page = request.page ?: 1)
            .map { response ->
                val listMovie = response.results?.mapNotNull { movie ->
                    MovieApiToUiConverter.convert(movie)
                } ?: emptyList()
                PagingData(
                    list = listMovie,
                    page = response.page,
                    totalPage = response.totalPages,
                    pagingState = PagingState.IDLE
                )
            }.onRight { data ->
                analyticsHelper.logMovieApi(page = data.page)
            }
    }
}