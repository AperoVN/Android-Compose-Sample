package com.apero.sample.data.network.interceptor

import com.apero.sample.data.prefs.app.AppPreferences
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author KO Huyn
 * @since 21/07/2023.
 */
class TmdbRetrofitParamsInterceptor(
    private val apiKey: String,
    private val appPreferences: AppPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val language = runBlocking { appPreferences.currentLanguage.firstOrNull() }
        val originalRequest = chain.request()
        val url = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey).apply {
                if (language != null) {
                    addQueryParameter("language", language)
                }
            }
            .build()
        val request = originalRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}
