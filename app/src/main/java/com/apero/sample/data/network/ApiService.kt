package com.apero.sample.data.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.apero.sample.data.network.response.base.ListResponse
import com.apero.sample.data.network.response.movie.MovieItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by KO Huyn.
 */
// FIXME: rename to TmdbApiService (The Movie DB) & move to com.apero.data.network.tmdb
interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int,
    ): Either<CallError, ListResponse<List<MovieItemResponse>>>
}