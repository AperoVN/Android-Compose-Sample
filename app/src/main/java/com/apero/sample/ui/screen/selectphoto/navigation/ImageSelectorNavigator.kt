package com.apero.sample.ui.screen.selectphoto.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.selectphoto.ImageSelectorRoute

fun NavController.navigateToSelectImage(navOptions: NavOptions? = null) {
    this.navigate(ImageSelectorNavigator.route, navOptions)
}

fun NavGraphBuilder.imageSelectorScreen(appState: AppState) {
    composable(
        route = ImageSelectorNavigator.route, arguments = ImageSelectorNavigator.arguments
    ) {
        ImageSelectorRoute(
            vm = hiltViewModel(),
            onNavigateUp = { appState.navigateUp() },
            onOpenSettingApp = { appState.openSettingApp() },
            onNavigateToPreview = { media ->
            }
        )
    }
}

object ImageSelectorNavigator : NavigationNoArg() {
    override val route: String
        get() = Route.IMAGE_SELECT
}
