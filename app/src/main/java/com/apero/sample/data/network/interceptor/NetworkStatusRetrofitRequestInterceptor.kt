package com.apero.sample.data.network.interceptor

import android.content.Context
import com.apero.sample.data.network.exception.NoInternetConnection
import com.apero.sample.utils.hasNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author KO Huyn.
 */
class NetworkStatusRetrofitRequestInterceptor(
    @ApplicationContext private val context: Context,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.hasNetwork()) {
            throw NoInternetConnection()
        }
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        return chain.proceed(request)
    }
}