package com.apero.sample.data.repository.movie

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.request.MoviePopularRequest
import com.apero.sample.data.state.PagingData
import com.apero.sample.data.state.ResultState

/**
 * Created by KO Huyn on 20/07/2023.
 */
interface IMovieRepository {
    suspend fun getListMoviePopular(request: MoviePopularRequest): Either<CallError, PagingData<Movie>>
}