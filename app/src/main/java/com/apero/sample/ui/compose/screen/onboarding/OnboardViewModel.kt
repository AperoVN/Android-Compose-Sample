package com.apero.sample.ui.compose.screen.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.data.repository.common.ICommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val commonRepository: ICommonRepository
) : ViewModel() {
    fun makeOpenedOnBoarding() {
        viewModelScope.launch {
            commonRepository.makeOpenedOnBoarding()
        }
    }
}
