package com.apero.sample.di

import com.apero.sample.MainViewModel
import com.apero.sample.ui.compose.screen.splash.SplashViewModel
import com.apero.sample.ui.compose.screen.onboarding.OnboardViewModel
import com.apero.sample.ui.compose.screen.selectphoto.ImageSelectorViewModel
import com.apero.sample.ui.compose.screen.setting.SettingViewModel
import com.apero.sample.ui.compose.screen.history.HistoryViewModel
import com.apero.sample.ui.compose.screen.home.HomeViewModel
import com.apero.sample.ui.compose.screen.setting.language.LanguageViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Created by KO Huyn on 31/08/2023.
 */

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::OnboardViewModel)
    viewModelOf(::ImageSelectorViewModel)
    viewModelOf(::SettingViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::LanguageViewModel)
}