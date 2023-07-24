package com.apero.sample.di

import com.apero.sample.data.network.ApiService
import com.apero.sample.data.prefs.IAppDataStore
import com.apero.sample.data.repository.common.CommonRepositoryImpl
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.data.repository.movie.IMovieRepository
import com.apero.sample.data.repository.movie.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by KO Huyn.
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(apiService: ApiService): IMovieRepository {
        return MovieRepositoryImpl(apiService)
    }

    @Provides
    fun provideCommonRepository(dataStore: IAppDataStore): ICommonRepository {
        return CommonRepositoryImpl(dataStore)
    }
}