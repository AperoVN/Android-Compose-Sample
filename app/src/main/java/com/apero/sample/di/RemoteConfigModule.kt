package com.apero.sample.di

import com.apero.sample.data.prefs.remoteconfig.IRemoteConfigDataStore
import com.apero.sample.data.remoteconfig.FirebaseRemoteConfigRepository
import com.apero.sample.data.remoteconfig.RemoteConfigRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by KO Huyn on 14/07/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {
    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        return remoteConfig
    }

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun provideRemoteConfig(
        crashlytics: FirebaseCrashlytics,
        remoteConfig: FirebaseRemoteConfig,
        dataStore: IRemoteConfigDataStore,
    ): RemoteConfigRepository {
        return FirebaseRemoteConfigRepository(crashlytics, remoteConfig, dataStore)
    }
}