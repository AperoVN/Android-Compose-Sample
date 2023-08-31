package com.apero.sample.ui.compose.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.apero.sample.R
import com.apero.sample.ui.compose.screen.splash.navigation.SplashDirectionType
import com.apero.sample.ui.compose.theme.ColorsGradient
import com.apero.sample.ui.compose.theme.FontMedium
import com.apero.sample.ui.compose.screen.TrackScreenViewEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

/**
 * Created by KO Huyn.
 */

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}

@Composable
fun SplashRoute(
    viewModel: SplashViewModel,
    onNavigateNextScreen: (type: SplashDirectionType) -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        viewModel.typeDirectionState.first().let(onNavigateNextScreen)
    }
    TrackScreenViewEvent(screenName = "Splash")
    SplashScreen()
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = BiasAlignment(
            horizontalBias = 0f,
            verticalBias = -0.4f
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 64.sp,
                    fontFamily = FontMedium,
                    fontWeight = FontWeight(600)
                ),
                modifier = Modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        val brush = Brush.verticalGradient(ColorsGradient)
                        onDrawWithContent {
                            drawContent()
                            drawRect(brush, blendMode = BlendMode.SrcAtop)
                        }
                    }
            )
        }
    }

}