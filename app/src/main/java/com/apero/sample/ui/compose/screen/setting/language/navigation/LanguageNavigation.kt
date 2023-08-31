package com.apero.sample.ui.compose.screen.setting.language.navigation

import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.apero.sample.ui.compose.navigation.Route
import com.apero.sample.ui.compose.navigation.controller.NavigationNoArg
import com.apero.sample.ui.compose.AppState
import com.apero.sample.ui.compose.screen.onboarding.navigation.navigateToOnBoarding
import com.apero.sample.ui.compose.screen.setting.language.LanguageRouter
import com.apero.sample.ui.compose.screen.setting.language.LanguageViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavController.navigateToLanguage(
    navOptions: NavOptions? = null
) {
    this.navigate(LanguageNavigation.route, navOptions)
}

fun NavGraphBuilder.languageScreen(appState: AppState) {
    composable(route = LanguageNavigation.route) {
        val vm: LanguageViewModel = koinViewModel()
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