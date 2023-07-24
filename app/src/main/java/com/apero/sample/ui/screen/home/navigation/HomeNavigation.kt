package com.apero.sample.ui.screen.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.home.HomeRoute
import com.apero.sample.ui.screen.selectphoto.navigation.navigateToSelectImage
import com.apero.sample.ui.screen.setting.navigation.navigateToSetting

/**
 * Created by KO Huyn on 20/07/2023.
 */


fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HomeNavigation.route, navOptions)
}

fun NavGraphBuilder.homeScreen(
    appState: AppState
) {
    composable(route = HomeNavigation.route) {
        HomeRoute(
            vm = hiltViewModel(),
            onClickGallery = {
                appState.navController.navigateToSelectImage()
            },
            onClickSetting = {
                appState.navController.navigateToSetting()
            },
            onClickMovieDetail = {
//                appState.navController.navigateToSelectImage()
            }
        )
    }
}

object HomeNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.HOME
}