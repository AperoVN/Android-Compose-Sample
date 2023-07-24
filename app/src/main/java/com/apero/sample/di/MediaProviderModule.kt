package com.apero.sample.di

import android.content.Context
import com.apero.sample.data.provider.media.MediaProvider
import com.apero.sample.data.provider.media.MediaProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by KO Huyn on 04/07/2023.
 */
@Module
@InstallIn(ViewModelComponent::class)
object MediaProviderModule {

    @Provides
    fun provideMediaProvider(@ApplicationContext context: Context): MediaProvider {
        return MediaProviderImpl(context)
    }
}