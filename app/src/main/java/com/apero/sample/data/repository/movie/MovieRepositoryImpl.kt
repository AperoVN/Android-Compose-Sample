package com.apero.sample.data.repository.movie

import com.apero.sample.data.converter.api.MovieApiToUiConverter
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.ApiService
import com.apero.sample.data.network.request.MoviePopularRequest
import com.apero.sample.data.state.PagingData
import com.apero.sample.data.state.PagingState
import com.apero.sample.data.state.ResultState

/**
 * Created by KO Huyn on 20/07/2023.
 */
class MovieRepositoryImpl(private val apiService: ApiService) : IMovieRepository {
    override suspend fun getListMoviePopular(request: MoviePopularRequest): ResultState<PagingData<Movie>> {
        return ResultState.fromApiResponse {
            apiService.getMoviePopular(page = request.page ?: 1, limit = request.limit)
        }.map { response ->
            val listMovie = response.results?.mapNotNull { movie ->
                MovieApiToUiConverter.convert(movie)
            } ?: emptyList()
            PagingData(
                list = listMovie,
                page = response.page,
                totalPage = response.totalPages,
                pagingState = PagingState.IDLE
            )
        }
    }
}