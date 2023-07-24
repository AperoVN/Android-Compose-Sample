package com.apero.sample.di

import com.apero.sample.config.Config
import com.apero.sample.di.qualifier.ApiKey
import com.apero.sample.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by KO Huyn on 21/07/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @BaseUrl
    @Provides
    fun providerBaseUrl(): String {
        return Config.BASE_URL
    }

    @ApiKey
    @Provides
    fun provideApiKey(): String {
        return Config.API_KEY
    }
}