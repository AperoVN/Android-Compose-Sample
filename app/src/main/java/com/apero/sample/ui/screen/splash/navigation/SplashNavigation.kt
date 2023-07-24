package com.apero.sample.ui.screen.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.home.navigation.navigateToHome
import com.apero.sample.ui.screen.onboarding.navigation.navigateToOnBoarding
import com.apero.sample.ui.screen.setting.language.navigation.navigateToLanguage
import com.apero.sample.ui.screen.setting.navigation.navigateToSetting
import com.apero.sample.ui.screen.splash.SplashRoute

/**
 * Created by KO Huyn.
 */

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    this.navigate(SplashNavigation.route, navOptions)
}

fun NavGraphBuilder.splashScreen(appState: AppState) {
    composable(route = SplashNavigation.route) {
        SplashRoute(
            viewModel = hiltViewModel(),
            onNavigateNextScreen = { type ->
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(SplashNavigation.route, inclusive = true)
                    .build()
                when (type) {
                    SplashDirectionType.TO_HOME -> {
                        appState.navController.navigateToHome(navOptions)
                    }

                    SplashDirectionType.TO_ON_BOARDING -> {
                        appState.navController.navigateToOnBoarding(navOptions)
                    }

                    SplashDirectionType.TO_FIRST_LANGUAGE -> {
                        appState.navController.navigateToLanguage(navOptions)
                    }
                }
            })
    }
}

object SplashNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.SPLASH
}

enum class SplashDirectionType {
    TO_HOME, TO_ON_BOARDING, TO_FIRST_LANGUAGE
}