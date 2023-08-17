package com.apero.sample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.apero.core.mediapicker.PhotoPickerArgs
import com.apero.core.mediapicker.installPhotoPickerLauncher
import com.apero.sample.R
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.crop.cropScreen
import com.apero.sample.ui.screen.crop.navigateToCrop
import com.apero.sample.ui.screen.history.navigation.historyScreen
import com.apero.sample.ui.screen.home.navigation.homeScreen
import com.apero.sample.ui.screen.onboarding.navigation.onBoardingScreen
import com.apero.sample.ui.screen.selectphoto.navigation.imageSelectorScreen
import com.apero.sample.ui.screen.setting.language.navigation.languageScreen
import com.apero.sample.ui.screen.setting.navigation.settingScreen
import com.apero.sample.ui.screen.splash.navigation.SplashNavigation
import com.apero.sample.ui.screen.splash.navigation.splashScreen
import timber.log.Timber

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
    val homePhotoPicker = installPhotoPickerLauncher(
        navController = appState.navController,
        routePostfix = "home_pickerFlow",
        args = PhotoPickerArgs(
            selectMin = 3, selectMax = 5,
            demoDrawables = listOf(
                R.drawable.img_onboard_1,
                R.drawable.img_onboard_2,
                R.drawable.img_onboard_3,
            )
        )
    )
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
        homeScreen(appState = appState, onNavigateToPicker = {
            homePhotoPicker.launch { /* no-op */ }
        })
        languageScreen(appState = appState)
        homePhotoPicker.install(
            onNavigateUp = appState::navigateUp,
            onMediasSelected = {
                appState.navController.navigateToCrop(it.head)
            },
        )
        cropScreen(
            onChangePhoto = {
                homePhotoPicker.launch { /* no-op */ }
            },
            onNavigateUp = appState::navigateUp,
        )
    }
}
