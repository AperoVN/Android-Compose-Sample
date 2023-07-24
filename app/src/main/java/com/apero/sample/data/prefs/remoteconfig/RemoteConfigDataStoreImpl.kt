package com.apero.sample.data.prefs.remoteconfig

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.apero.sample.data.prefs.get
import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn on 14/07/2023.
 */
class RemoteConfigDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : IRemoteConfigDataStore {

    override suspend fun setHomeSpanCount(spanCount: Int?) {
        dataStore.edit { preferences ->
            preferences[RemoteConfigPreferencesKeys.KEY_SPAN_COUNT_HOME] = spanCount ?: 3
        }
    }

    override fun getHomeSpanCount(): Flow<Int?> {
        return dataStore.get(RemoteConfigPreferencesKeys.KEY_SPAN_COUNT_HOME)
    }
}