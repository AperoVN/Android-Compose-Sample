package com.apero.sample.di

import com.apero.sample.config.Config
import com.apero.sample.di.qualifier.UrlQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by KO Huyn on 21/07/2023.
 */

val applicationModule = module {
    single(named(UrlQualifier.BaseUrl)) { Config.BASE_URL }
    single(named(UrlQualifier.ApiKey)) { Config.API_KEY }
}