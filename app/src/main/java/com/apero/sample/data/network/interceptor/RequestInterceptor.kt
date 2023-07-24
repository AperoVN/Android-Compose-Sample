package com.apero.sample.data.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.apero.sample.data.network.exception.NoInternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by KO Huyn.
 */
class RequestInterceptor(@ApplicationContext private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.hasNetwork()) {
            throw NoInternetConnection()
        }
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        return chain.proceed(request)
    }

    private fun Context.hasNetwork(): Boolean {
        val connectivityManager = getSystemService<ConnectivityManager>()
        return connectivityManager?.activeNetwork
            ?.let(connectivityManager::getNetworkCapabilities)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}