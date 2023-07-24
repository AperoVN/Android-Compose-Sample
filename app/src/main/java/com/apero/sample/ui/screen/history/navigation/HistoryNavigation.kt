package com.apero.sample.ui.screen.history.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.history.HistoryRoute

fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.navigate(HistoryNavigation.route, navOptions)
}

fun NavGraphBuilder.historyScreen(
    appState: AppState
) {
    composable(route = HistoryNavigation.route) {
        HistoryRoute(
            viewModel = hiltViewModel(),
            onNavigateUp = { appState.navigateUp() }
        )
    }
}

object HistoryNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.HISTORY
}