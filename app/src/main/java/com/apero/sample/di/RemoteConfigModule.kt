package com.apero.sample.di

import com.apero.sample.data.remoteconfig.FirebaseRemoteConfigRepository
import com.apero.sample.data.remoteconfig.RemoteConfigRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by KO Huyn on 14/07/2023.
 */

val remoteConfigModule = module {
    single {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig
    }

    single { FirebaseCrashlytics.getInstance() }

    singleOf(::FirebaseRemoteConfigRepository){ bind<RemoteConfigRepository>() }
}