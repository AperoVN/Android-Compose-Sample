package com.apero.sample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.history.navigation.historyScreen
import com.apero.sample.ui.screen.home.navigation.homeScreen
import com.apero.sample.ui.screen.onboarding.navigation.onBoardingScreen
import com.apero.sample.ui.screen.selectphoto.navigation.imageSelectorScreen
import com.apero.sample.ui.screen.setting.language.navigation.languageScreen
import com.apero.sample.ui.screen.setting.navigation.settingScreen
import com.apero.sample.ui.screen.splash.navigation.SplashNavigation
import com.apero.sample.ui.screen.splash.navigation.splashScreen

/**
 * Created by KO Huyn.
 */
@Composable
fun AppNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: String = SplashNavigation.route
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashScreen(appState = appState)
        onBoardingScreen(appState = appState)
        imageSelectorScreen(appState = appState)
        settingScreen(appState = appState)
        historyScreen(appState = appState)
        homeScreen(appState = appState)
        languageScreen(appState = appState)
    }
}
