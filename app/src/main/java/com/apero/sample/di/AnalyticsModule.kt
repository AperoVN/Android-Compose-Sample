package com.apero.sample.di

import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.analytics.FirebaseAnalyticsHelper
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by KO Huyn on 25/07/2023.
 */

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(get()) }
    singleOf(::FirebaseAnalyticsHelper) { bind<AnalyticsHelper>() }
}