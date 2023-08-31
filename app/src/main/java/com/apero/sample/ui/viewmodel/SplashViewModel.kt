package com.apero.sample.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.ui.compose.screen.splash.navigation.SplashDirectionType

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SplashViewModel(commonRepository: ICommonRepository) : ViewModel() {
    val typeDirectionState: Flow<SplashDirectionType> = combine(
        commonRepository.needOpenFirstLanguage(),
        commonRepository.needOpenOnBoarding()
    ) { needOpenFirstLanguage, needOpenOnBoarding ->
        when {
            needOpenFirstLanguage -> SplashDirectionType.TO_FIRST_LANGUAGE
            needOpenOnBoarding -> SplashDirectionType.TO_ON_BOARDING
            else -> SplashDirectionType.TO_HOME
        }
    }
}