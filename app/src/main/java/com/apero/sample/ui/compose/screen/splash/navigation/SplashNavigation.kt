package com.apero.sample.ui.compose.screen.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.home.navigation.navigateToHome
import com.apero.sample.ui.compose.screen.onboarding.navigation.navigateToOnBoarding
import com.apero.sample.ui.compose.screen.setting.language.navigation.navigateToLanguage
import com.apero.sample.ui.compose.screen.splash.SplashRoute

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