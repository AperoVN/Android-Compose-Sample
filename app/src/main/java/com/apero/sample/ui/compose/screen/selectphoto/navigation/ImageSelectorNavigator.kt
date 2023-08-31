package com.apero.sample.ui.compose.screen.selectphoto.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.selectphoto.ImageSelectorRoute
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToSelectImage(navOptions: NavOptions? = null) {
    this.navigate(ImageSelectorNavigator.route, navOptions)
}

fun NavGraphBuilder.imageSelectorScreen(appState: AppState) {
    composable(
        route = ImageSelectorNavigator.route, arguments = ImageSelectorNavigator.arguments
    ) {
        ImageSelectorRoute(
            vm = koinViewModel(),
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
