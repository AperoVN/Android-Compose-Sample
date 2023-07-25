package com.apero.sample.di.qualifier

import javax.inject.Qualifier

/**
 * Created by KO Huyn on 25/07/2023.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsFirebase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsConsole
