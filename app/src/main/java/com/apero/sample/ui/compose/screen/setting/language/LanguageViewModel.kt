package com.apero.sample.ui.compose.screen.setting.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.data.model.Language
import com.apero.sample.data.repository.common.ICommonRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageViewModel(private val commonRepository: ICommonRepository) :
    ViewModel() {
    private val listLanguageState = flowOf(Language.values().toList())
    private val languageDefaultState = commonRepository.getLanguage()
    private val languageSelectedState = MutableStateFlow<Language?>(null)

    val languageUiState = combine(
        listLanguageState,
        languageDefaultState,
        languageSelectedState
    ) { list, languageDefault, languageSelector ->
        val languageSelected = languageSelector ?: languageDefault
        LanguageUiState(
            listLanguage = list,
            languageDefault = languageDefault,
            languageSelected = languageSelected
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LanguageUiState()
    )

    val needOpenLanguage: Flow<Boolean> = commonRepository.needOpenFirstLanguage()

    fun makeLanguageOpened() {
        viewModelScope.launch {
            commonRepository.makeOpenedFirstLanguage()
        }
    }

    fun setLanguageSelected(languageSelected: Language) {
        viewModelScope.launch {
            languageSelectedState.update { languageSelected }
        }
    }

    fun saveCurrentLanguage() {
        val currentLanguageSelected = languageUiState.value.languageSelected
        if (currentLanguageSelected != null) {
            viewModelScope.launch {
                commonRepository.saveLanguage(currentLanguageSelected)
            }
        }
    }
}

data class LanguageUiState(
    val listLanguage: List<Language> = emptyList(),
    val languageDefault: Language? = null,
    val languageSelected: Language? = null
)