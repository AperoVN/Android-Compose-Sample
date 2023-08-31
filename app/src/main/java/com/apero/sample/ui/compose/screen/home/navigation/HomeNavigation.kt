package com.apero.sample.ui.compose.screen.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.home.HomeRoute
import com.apero.sample.ui.compose.screen.selectphoto.navigation.navigateToSelectImage
import com.apero.sample.ui.compose.screen.setting.navigation.navigateToSetting
import org.koin.androidx.compose.koinViewModel

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
            vm = koinViewModel(),
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