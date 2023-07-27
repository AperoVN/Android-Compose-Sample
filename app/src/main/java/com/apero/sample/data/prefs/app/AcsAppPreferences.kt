package com.apero.sample.data.prefs.app

import kotlinx.coroutines.flow.Flow

/**
 * Android-Compose-Sample App's Preferences
 *
 * @author KO Huyn.
 */
interface AcsAppPreferences {
    val isOnboardOpened: Flow<Boolean>

    suspend fun setOnboardOpened(isFirst: Boolean)

    val isLanguageOpened: Flow<Boolean>

    suspend fun setLanguageOpened(isFirst: Boolean)

    val currentLanguage: Flow<String>

    suspend fun setLanguage(language: String)

    val isFirstTimeAskingPermission: Flow<Boolean>

    suspend fun hasFirstTimeAskingPermissions()
}