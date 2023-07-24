package com.apero.sample

import androidx.lifecycle.ViewModel
import com.apero.sample.data.prefs.IAppDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(dataStore: IAppDataStore): ViewModel() {
    val languageState = dataStore.currentLanguage()
}
