package com.apero.sample.data.prefs

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by KO Huyn.
 */
object PreferencesKeys {
    val KEY_OPEN_ONBOARD_FIRST = booleanPreferencesKey("KEY_OPEN_APP_FIRST")
    val KEY_OPEN_LANGUAGE_FIRST = booleanPreferencesKey("KEY_OPEN_LANGUAGE_FIRST")
    val KEY_LANGUAGE = stringPreferencesKey("KEY_LANGUAGE")
    val KEY_FIRST_ASK_PERMISSION = booleanPreferencesKey("KEY_FIRST_ASK_PERMISSION")

}