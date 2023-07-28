package com.apero.sample.di

import android.content.Context
import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import coil.ImageLoader
import com.apero.sample.BuildConfig
import com.apero.sample.data.network.TmdbApiService
import com.apero.sample.data.network.interceptor.TmdbRetrofitParamsInterceptor
import com.apero.sample.data.network.interceptor.NetworkStatusRetrofitRequestInterceptor
import com.apero.sample.data.network.monitor.NetworkMonitorImpl
import com.apero.sample.data.network.monitor.NetworkMonitor
import com.apero.sample.data.prefs.app.AcsAppPreferences
import com.apero.sample.di.qualifier.ApiKey
import com.apero.sample.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author KO Huyn.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        networkMonitor: NetworkMonitor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(NetworkStatusRetrofitRequestInterceptor(networkMonitor))
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .build()
    }

    @Provides
    @Singleton
    fun getApiService(
        okHttpClient: OkHttpClient,
        @BaseUrl url: String,
        @ApiKey apiKey: String,
        acsAppPreferences: AcsAppPreferences
    ): TmdbApiService {
        return Retrofit.Builder()
            .client(
                okHttpClient.newBuilder()
                    .addInterceptor(TmdbRetrofitParamsInterceptor(apiKey = apiKey, acsAppPreferences = acsAppPreferences))
                    .build()
            )
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

    @Provides
    fun provideNetworkMonitor(
        impl: NetworkMonitorImpl,
    ): NetworkMonitor = impl
}