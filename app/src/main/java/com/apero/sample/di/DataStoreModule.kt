package com.apero.sample.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.apero.sample.data.prefs.app.DatastoreAppPreferences
import com.apero.sample.data.prefs.remoteconfig.IRemoteConfigDataStore
import com.apero.sample.data.prefs.remoteconfig.RemoteConfigDataStoreImpl
import com.apero.sample.di.qualifier.AppDataStore
import com.apero.sample.di.qualifier.RemoteConfigDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author KO Huyn
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val APP_PREFERENCES_NAME = "app_preferences"
    private const val REMOTE_CONFIG_PREFERENCES_NAME = "remote_config_preferences"

    private val Context.appDataStore by preferencesDataStore(name = APP_PREFERENCES_NAME)

    private val Context.remoteConfigDataStore by preferencesDataStore(name = REMOTE_CONFIG_PREFERENCES_NAME)

    @Provides
    @Singleton
    @AppDataStore
    fun provideAppDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.appDataStore
    }

    @Provides
    @Singleton
    fun provideIAppDataStore(@AppDataStore dataStore: DataStore<Preferences>): com.apero.sample.data.prefs.app.AppPreferences {
        return DatastoreAppPreferences(dataStore)
    }

    @Provides
    @Singleton
    @RemoteConfigDataStore
    fun provideRemoteConfigDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.remoteConfigDataStore
    }

    @Provides
    @Singleton
    fun provideIRemoteConfigDataStore(@RemoteConfigDataStore dataStore: DataStore<Preferences>): IRemoteConfigDataStore {
        return RemoteConfigDataStoreImpl(dataStore)
    }
}
