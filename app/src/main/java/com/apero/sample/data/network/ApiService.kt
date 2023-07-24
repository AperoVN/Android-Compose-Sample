package com.apero.sample.data.network

import com.apero.sample.data.network.response.base.ListResponse
import com.apero.sample.data.network.response.movie.MovieItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by KO Huyn.
 */
interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("page") page: Int
    ): Response<ListResponse<List<MovieItemResponse>>>
}
