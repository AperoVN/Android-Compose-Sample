package com.apero.sample.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by KO Huyn on 21/07/2023.
 */
class ParamsInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val request = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}