package com.apero.sample.di.qualifier

import javax.inject.Qualifier

/**
 * Created by KO Huyn.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

enum class UrlQualifier {
    BaseUrl, ApiKey
}