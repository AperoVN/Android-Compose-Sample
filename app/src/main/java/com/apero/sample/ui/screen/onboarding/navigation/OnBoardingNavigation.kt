package com.apero.sample.ui.screen.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.home.navigation.navigateToHome
import com.apero.sample.ui.screen.onboarding.OnBoardingRoute

/**
 * Created by KO Huyn.
 */

fun NavController.navigateToOnBoarding(navOptions: NavOptions? = null) {
    this.navigate(OnBoardingNavigation.route, navOptions)
}

fun NavGraphBuilder.onBoardingScreen(appState: AppState) {
    composable(route = OnBoardingNavigation.route) {
        OnBoardingRoute(
            vm = hiltViewModel(),
            onNavigateToHome = { appState.navController.navigateToHome() })
    }
}

object OnBoardingNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.ON_BOARDING
}