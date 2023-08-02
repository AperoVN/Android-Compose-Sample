package com.apero.sample.data.prefs.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.apero.sample.data.model.Language
import com.apero.sample.data.prefs.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author KO Huyn
 */
class DatastoreAppPreferences(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {
    override val isOnboardOpened: Flow<Boolean> =
        dataStore[AppPreferencesKeys.KEY_OPEN_ONBOARD_FIRST]
            .map { it ?: false }

    override suspend fun setOnboardOpened(isFirst: Boolean) {
        dataStore.edit { preferences ->
            preferences[AppPreferencesKeys.KEY_OPEN_ONBOARD_FIRST] = isFirst
        }
    }

    override val isLanguageOpened: Flow<Boolean> =
        dataStore[AppPreferencesKeys.KEY_OPEN_LANGUAGE_FIRST]
            .map { it ?: false }

    override suspend fun setLanguageOpened(isFirst: Boolean) {
        dataStore.edit { preferences ->
            preferences[AppPreferencesKeys.KEY_OPEN_LANGUAGE_FIRST] = isFirst
        }
    }

    override val currentLanguage: Flow<String> =
        dataStore[AppPreferencesKeys.KEY_LANGUAGE]
            .map { it ?: Language.getDefault().countryCode }

    override suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[AppPreferencesKeys.KEY_LANGUAGE] = language
        }
    }

    override val isFirstTimeAskingPermission: Flow<Boolean> =
        dataStore[AppPreferencesKeys.KEY_FIRST_ASK_PERMISSION]
            .map { it ?: true }

    override suspend fun hasFirstTimeAskingPermissions() {
        dataStore.edit { preferences ->
            preferences[AppPreferencesKeys.KEY_FIRST_ASK_PERMISSION] = false
        }
    }
}
