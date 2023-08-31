package com.apero.sample.ui.compose.screen.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.home.navigation.navigateToHome
import com.apero.sample.ui.compose.screen.onboarding.OnBoardingRoute
import org.koin.androidx.compose.koinViewModel

/**
 * Created by KO Huyn.
 */

fun NavController.navigateToOnBoarding(navOptions: NavOptions? = null) {
    this.navigate(OnBoardingNavigation.route, navOptions)
}

fun NavGraphBuilder.onBoardingScreen(appState: AppState) {
    composable(route = OnBoardingNavigation.route) {
        OnBoardingRoute(
            vm = koinViewModel(),
            onNavigateToHome = { appState.navController.navigateToHome() })
    }
}

object OnBoardingNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.ON_BOARDING
}