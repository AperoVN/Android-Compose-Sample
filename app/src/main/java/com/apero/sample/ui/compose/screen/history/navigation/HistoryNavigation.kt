package com.apero.sample.ui.compose.screen.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.history.HistoryRoute
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToHistory(navOptions: NavOptions? = null) {
    this.navigate(HistoryNavigation.route, navOptions)
}

fun NavGraphBuilder.historyScreen(
    appState: AppState
) {
    composable(route = HistoryNavigation.route) {
        HistoryRoute(
            viewModel = koinViewModel(),
            onNavigateUp = { appState.navigateUp() }
        )
    }
}

object HistoryNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.HISTORY
}