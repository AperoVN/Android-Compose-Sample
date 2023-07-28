package com.apero.sample.di

import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.data.network.TmdbApiService
import com.apero.sample.data.prefs.app.AcsAppPreferences
import com.apero.sample.data.repository.common.CommonRepositoryImpl
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.data.repository.movie.IMovieRepository
import com.apero.sample.data.repository.movie.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * @author KO Huyn.
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(
        tmdbApiService: TmdbApiService,
        analyticsHelper: AnalyticsHelper,
    ): IMovieRepository {
        return MovieRepositoryImpl(tmdbApiService, analyticsHelper)
    }

    @Provides
    fun provideCommonRepository(dataStore: AcsAppPreferences): ICommonRepository {
        return CommonRepositoryImpl(dataStore)
    }
}