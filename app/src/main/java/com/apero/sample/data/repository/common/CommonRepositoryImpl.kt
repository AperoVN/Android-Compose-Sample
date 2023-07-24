package com.apero.sample.data.repository.common

import com.apero.sample.data.model.Language
import com.apero.sample.data.prefs.app.IAppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

/**
 * Created by KO Huyn on 24/07/2023.
 */
class CommonRepositoryImpl(private val dataStore: IAppDataStore) : ICommonRepository {
    override fun getLanguage(): Flow<Language> {
        return dataStore.currentLanguage().mapNotNull { Language.findFromCountryCode(it) }
    }

    override fun needOpenOnBoarding(): Flow<Boolean> {
        return dataStore.isOnboardOpened().map { it.not() }
    }

    override fun needOpenFirstLanguage(): Flow<Boolean> {
        return dataStore.isLanguageOpened().map { it.not() }
    }

    override suspend fun saveLanguage(language: Language) {
        dataStore.setLanguage(language.countryCode)
    }

    override suspend fun makeOpenedOnBoarding() {
        dataStore.setOnboardOpened(true)
    }

    override suspend fun makeOpenedFirstLanguage() {
        dataStore.setLanguageOpened(true)
    }
}