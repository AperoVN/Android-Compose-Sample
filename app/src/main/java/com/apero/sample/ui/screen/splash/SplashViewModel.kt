package com.apero.sample.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.apero.sample.data.prefs.IAppDataStore
import com.apero.sample.data.repository.common.CommonRepositoryImpl
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.ui.screen.splash.navigation.SplashDirectionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    commonRepository: ICommonRepository
) : ViewModel() {
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