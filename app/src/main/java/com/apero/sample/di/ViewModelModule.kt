package com.apero.sample.di

import com.apero.sample.MainViewModel
import com.apero.sample.ui.viewmodel.SplashViewModel
import com.apero.sample.ui.viewmodel.OnboardViewModel
import com.apero.sample.ui.viewmodel.ImageSelectorViewModel
import com.apero.sample.ui.viewmodel.SettingViewModel
import com.apero.sample.ui.viewmodel.HistoryViewModel
import com.apero.sample.ui.viewmodel.HomeViewModel
import com.apero.sample.ui.viewmodel.LanguageViewModel
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