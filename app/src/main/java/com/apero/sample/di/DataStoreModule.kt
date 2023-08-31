package com.apero.sample.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.apero.sample.data.prefs.app.AppPreferences
import com.apero.sample.data.prefs.app.DatastoreAppPreferences
import com.apero.sample.data.prefs.remoteconfig.IRemoteConfigDataStore
import com.apero.sample.data.prefs.remoteconfig.RemoteConfigDataStoreImpl
import com.apero.sample.di.qualifier.DataStoreQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author KO Huyn
 */
private const val APP_PREFERENCES_NAME = "app_preferences"
private const val REMOTE_CONFIG_PREFERENCES_NAME = "remote_config_preferences"

private val Context.appDataStore by preferencesDataStore(name = APP_PREFERENCES_NAME)

private val Context.remoteConfigDataStore by preferencesDataStore(name = REMOTE_CONFIG_PREFERENCES_NAME)

val dataStoreModule = module {
    single(named(DataStoreQualifier.AppDataStore)) {
        get<Context>().appDataStore
    }

    single<AppPreferences> {
        DatastoreAppPreferences(get(named(DataStoreQualifier.AppDataStore)))
    }

    single(named(DataStoreQualifier.RemoteConfigDataStore)) {
        get<Context>().remoteConfigDataStore
    }

    single<IRemoteConfigDataStore> {
        RemoteConfigDataStoreImpl(get(named(DataStoreQualifier.RemoteConfigDataStore)))
    }
}
