package com.apero.sample.ui.compose.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.data.repository.common.ICommonRepository

import kotlinx.coroutines.launch
import javax.inject.Inject

class OnboardViewModel(private val commonRepository: ICommonRepository) : ViewModel() {
    fun makeOpenedOnBoarding() {
        viewModelScope.launch {
            commonRepository.makeOpenedOnBoarding()
        }
    }
}
