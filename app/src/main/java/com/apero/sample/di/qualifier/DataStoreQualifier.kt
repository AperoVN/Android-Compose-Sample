package com.apero.sample.di.qualifier

import javax.inject.Qualifier

/**
 * Created by KO Huyn on 24/07/2023.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteConfigDataStore

enum class DataStoreQualifier {
    AppDataStore, RemoteConfigDataStore
}