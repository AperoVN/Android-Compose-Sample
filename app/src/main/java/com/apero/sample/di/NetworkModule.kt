package com.apero.sample.di

import android.content.Context
import coil.ImageLoader
import com.apero.sample.BuildConfig
import com.apero.sample.data.network.ApiService
import com.apero.sample.data.network.interceptor.ParamsInterceptor
import com.apero.sample.data.network.interceptor.RequestInterceptor
import com.apero.sample.data.network.monitor.NetworkMonitorImpl
import com.apero.sample.data.network.monitor.INetworkMonitor
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
 * Created by KO Huyn.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor(context))
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
        @ApiKey apiKey: String
    ): ApiService {
        return Retrofit.Builder()
            .client(
                okHttpClient.newBuilder()
                    .addInterceptor(ParamsInterceptor(apiKey))
                    .build()
            )
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): INetworkMonitor {
        return NetworkMonitorImpl(context)
    }
}