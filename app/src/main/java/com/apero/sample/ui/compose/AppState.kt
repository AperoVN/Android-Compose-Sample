package com.apero.sample.ui.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.apero.sample.data.state.UiText
import com.apero.sample.ui.compose.screen.onboarding.navigation.OnBoardingNavigation
import com.apero.sample.ui.compose.screen.splash.navigation.SplashNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by KO Huyn.
 */

@Composable
fun rememberAppState(
    context: Context,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    return remember(context, navController, coroutineScope) {
        AppState(context, navController, coroutineScope)
    }
}

data class AppState(
    val context: Context,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowOverlapStatusBar = navController.currentBackStackEntryFlow
        .map { navBackStack ->
            listOf(
                SplashNavigation.route,
                OnBoardingNavigation.route
            ).contains(navBackStack.destination.route)
        }.distinctUntilChanged()
        .onEach { Log.d("shouldShowOverlapStatusBar",it.toString()) }

    val isFinish: Boolean get() = navController.previousBackStackEntry == null

    fun showSnackBar(message: String) {
        coroutineScope.launch {
            //todo
        }
    }

    fun showSnackBar(message: UiText) {
        showSnackBar(message.getBy(context))
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun openSettingApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${context.packageName}")
        context.startActivity(intent)
    }
}