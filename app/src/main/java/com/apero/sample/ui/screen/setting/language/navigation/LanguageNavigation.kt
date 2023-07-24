package com.apero.sample.ui.screen.setting.language.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.navigation.Route
import com.apero.sample.navigation.controller.NavigationNoArg
import com.apero.sample.ui.AppState
import com.apero.sample.ui.screen.onboarding.navigation.navigateToOnBoarding
import com.apero.sample.ui.screen.setting.language.LanguageRouter
import com.apero.sample.ui.screen.setting.language.LanguageViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

fun NavController.navigateToLanguage(
    navOptions: NavOptions? = null
) {
    this.navigate(LanguageNavigation.route, navOptions)
}

fun NavGraphBuilder.languageScreen(appState: AppState) {
    composable(route = LanguageNavigation.route) {
        val vm: LanguageViewModel = hiltViewModel()
        val coroutineScope = rememberCoroutineScope()
        LanguageRouter(
            viewModel = vm,
            onNavigateUp = appState::navigateUp,
            onNavigateNext = {
                coroutineScope.launch {
                    val needOpenLanguage = vm.needOpenLanguage.firstOrNull() ?: true
                    if (needOpenLanguage) {
                        vm.makeLanguageOpened()
                        appState.navController.navigateToOnBoarding(
                            NavOptions.Builder()
                                .setPopUpTo(LanguageNavigation.route, inclusive = true)
                                .build()
                        )
                    } else {
                        appState.navigateUp()
                    }
                }
            }
        )
    }
}

object LanguageNavigation : NavigationNoArg() {
    override val route: String
        get() = Route.LANGUAGE
}