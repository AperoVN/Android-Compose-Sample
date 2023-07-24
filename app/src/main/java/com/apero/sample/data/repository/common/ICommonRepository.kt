package com.apero.sample.data.repository.common

import com.apero.sample.data.model.Language
import kotlinx.coroutines.flow.Flow

/**
 * Created by KO Huyn on 20/07/2023.
 */
interface ICommonRepository {
    fun getLanguage(): Flow<Language>
    fun needOpenOnBoarding(): Flow<Boolean>
    fun needOpenFirstLanguage(): Flow<Boolean>
    suspend fun saveLanguage(language: Language)
    suspend fun makeOpenedOnBoarding()
    suspend fun makeOpenedFirstLanguage()
}