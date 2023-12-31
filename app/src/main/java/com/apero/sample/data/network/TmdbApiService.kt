package com.apero.sample.data.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.apero.sample.data.network.response.base.ListResponse
import com.apero.sample.data.network.response.movie.MovieItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author KO Huyn.
 */
interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
    ): Either<CallError, ListResponse<List<MovieItemResponse>>>
}