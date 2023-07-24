package com.apero.sample.ui.screen.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apero.sample.BuildConfig
import com.apero.sample.R
import com.apero.sample.data.repository.common.ICommonRepository
import com.apero.sample.data.state.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(commonRepository: ICommonRepository) : ViewModel() {
    private val listSettingState = flowOf(settingList)
    private val languageState = commonRepository.getLanguage()

    val settingUiState = combine(listSettingState, languageState) { settings, language ->
        SettingUiState(listSetting = settings.map {
            if (it is SettingItem.SettingLanguage) {
                it.copy(language = language.countryName)
            } else it
        })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingUiState()
    )
}

data class SettingUiState(
    val listSetting: List<SettingItem> = emptyList()
)

sealed class SettingItem(
    @DrawableRes val iconResStart: Int,
    @StringRes val titleRes: Int,
    val value: UiText? = null,
    @DrawableRes val iconResEnd: Int? = R.drawable.ic_setting_arrow_right
) {
    data class SettingLanguage(var language: UiText?) : SettingItem(
        iconResStart = R.drawable.ic_setting_language,
        titleRes = R.string.setting_language,
        value = language,
    )

    object PrivacyPolicy : SettingItem(
        iconResStart = R.drawable.ic_setting_privacy_policy,
        titleRes = R.string.setting_privacy_policy,
    )

    object TermsOfService : SettingItem(
        iconResStart = R.drawable.ic_setting_terms_of_service,
        titleRes = R.string.setting_terms_of_service,
    )

    object ShareApp : SettingItem(
        iconResStart = R.drawable.ic_setting_share_app,
        titleRes = R.string.setting_share_app,
    )

    object Version : SettingItem(
        iconResStart = R.drawable.ic_setting_version,
        titleRes = R.string.setting_version,
        value = UiText.from(BuildConfig.VERSION_NAME),
        iconResEnd = null
    )
}

val settingList = listOf(
    SettingItem.SettingLanguage(null),
    SettingItem.PrivacyPolicy,
    SettingItem.TermsOfService,
    SettingItem.ShareApp,
    SettingItem.Version
)