package com.apero.sample.ui.screen.setting.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.setting.SettingRoute
import com.apero.sample.ui.screen.setting.language.navigation.navigateToLanguage

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(SettingNavigation.route, navOptions)
}

fun NavGraphBuilder.settingScreen(appState: AppState) {
    composable(route = SettingNavigation.route) {
        SettingRoute(
            viewModel = hiltViewModel(),
            onNavigationUp = { appState.navigateUp() },
            onNavigateToLanguage = { appState.navController.navigateToLanguage() }
        )
    }
}

object SettingNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.SETTING
}