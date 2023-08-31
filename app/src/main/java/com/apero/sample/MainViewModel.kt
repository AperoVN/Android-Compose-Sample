package com.apero.sample

import androidx.lifecycle.ViewModel
import com.apero.sample.data.prefs.app.AppPreferences

import javax.inject.Inject

class MainViewModel(dataStore: AppPreferences): ViewModel() {
    val languageState = dataStore.currentLanguage
}
