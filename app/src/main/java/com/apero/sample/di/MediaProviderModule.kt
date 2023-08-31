package com.apero.sample.di

import com.apero.sample.data.provider.media.MediaProvider
import com.apero.sample.data.provider.media.MediaProviderImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Created by KO Huyn on 04/07/2023.
 */

val mediaProviderModule = module {
    factoryOf(::MediaProviderImpl) { bind<MediaProvider>() }
}