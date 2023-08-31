package com.apero.sample.di

import com.apero.sample.data.repository.common.CommonRepositoryImpl
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.data.repository.movie.IMovieRepository
import com.apero.sample.data.repository.movie.MovieRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * @author KO Huyn.
 */

val repositoryModule = module {
    factoryOf(::MovieRepositoryImpl) { bind<IMovieRepository>() }
    factoryOf(::CommonRepositoryImpl) { bind<ICommonRepository>() }
}