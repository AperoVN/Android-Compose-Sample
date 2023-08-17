package com.apero.sample.ui.screen.crop

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.cropScreen(
    onChangePhoto: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    composable(
        route = "crop/{args}",
        arguments = listOf(
            navArgument("args") {
                type = CropScreenArgs.Type
                nullable = false
            }
        )
    ) {
        PsCropRoute(
            onNavigateUp = onNavigateUp,
            onChangePhoto = onChangePhoto,
        )
    }
}

fun NavController.navigateToCrop(image: Uri, navOptions: NavOptions? = null) {
    val args = Uri.encode(Json.encodeToString(CropScreenArgs(image.toString())))
    navigate(
        route = "crop/$args",
        navOptions = navOptions
    )
}
