package com.apero.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.apero.sample.analytics.AnalyticsHelper
import com.apero.sample.analytics.LocalAnalyticsHelper
import com.apero.sample.navigation.AppNavHost
import com.apero.sample.ui.rememberAppState
import com.apero.sample.ui.theme.ColorBackground
import com.apero.sample.ui.theme.ColorPrimary
import com.apero.sample.utils.UpdateLanguage
import com.apero.sample.utils.ifOrIgnore
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            systemUiController.isNavigationBarVisible = false
            systemUiController.setStatusBarColor(Color.Black)
            val language by viewModel.languageState.collectAsState(initial = null)
            language?.let { UpdateLanguage(it) }
            val appState = rememberAppState(this)
            val shouldShowOverlapStatusBar by appState.shouldShowOverlapStatusBar.collectAsState(
                initial = false
            )
            LaunchedEffect(shouldShowOverlapStatusBar) {
                systemUiController.setStatusBarColor(if (shouldShowOverlapStatusBar) Color.Transparent else ColorPrimary)
            }
            BackHandler {
                if (appState.isFinish) {
                    finish()
                }
            }
            CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
                AppNavHost(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .ifOrIgnore(!shouldShowOverlapStatusBar) {
                            statusBarsPadding()
                        }
                        .fillMaxSize()
                        .background(ColorBackground),
                    appState = appState,
                )
            }
        }
    }
}

