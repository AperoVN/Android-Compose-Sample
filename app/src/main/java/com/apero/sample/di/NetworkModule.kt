package com.apero.sample.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import coil.ImageLoader
import com.apero.sample.BuildConfig
import com.apero.sample.data.network.TmdbApiService
import com.apero.sample.data.network.interceptor.NetworkStatusRetrofitRequestInterceptor
import com.apero.sample.data.network.interceptor.TmdbRetrofitParamsInterceptor
import com.apero.sample.data.network.monitor.NetworkMonitor
import com.apero.sample.data.network.monitor.NetworkMonitorImpl
import com.apero.sample.di.qualifier.UrlQualifier
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author KO Huyn.
 */

val networkModule = module {
    singleOf(::NetworkMonitorImpl) { bind<NetworkMonitor>() }
    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(NetworkStatusRetrofitRequestInterceptor(get()))
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.build()
    }
    single {
        ImageLoader.Builder(get())
            .okHttpClient { get() }
            .build()
    }

    single<TmdbApiService> {
        Retrofit.Builder()
            .client(
                get<OkHttpClient>().newBuilder()
                    .addInterceptor(
                        TmdbRetrofitParamsInterceptor(
                            apiKey = get(named(UrlQualifier.ApiKey)),
                            appPreferences = get()
                        )
                    )
                    .build()
            ).baseUrl(get<String>(named(UrlQualifier.BaseUrl)))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }
}