package com.apero.sample.data.network.interceptor

import com.apero.sample.data.network.exception.NoInternetConnection
import com.apero.sample.data.network.monitor.NetworkMonitor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author KO Huyn.
 */
class NetworkStatusRetrofitRequestInterceptor(
    private val networkMonitor: NetworkMonitor,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        if (!networkMonitor.isOnline.first()) {
            throw NoInternetConnection()
        }
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        chain.proceed(request)
    }
}