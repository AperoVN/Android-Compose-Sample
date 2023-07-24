package com.apero.sample.data.prefs

import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn.
 */
interface IAppDataStore {
    fun isOnboardOpened(): Flow<Boolean>
    suspend fun setOnboardOpened(isFirst: Boolean)
    fun isLanguageOpened(): Flow<Boolean>
    suspend fun setLanguageOpened(isFirst: Boolean)
    fun currentLanguage(): Flow<String>
    suspend fun setLanguage(language: String)
    fun isFirstTimeAskingPermission(): Flow<Boolean>
    suspend fun hasFirstTimeAskingPermissions()
}