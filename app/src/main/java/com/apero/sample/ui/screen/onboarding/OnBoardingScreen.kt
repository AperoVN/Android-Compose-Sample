package com.apero.sample.ui.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apero.sample.R
import com.apero.sample.data.model.OnBoardingData
import com.apero.sample.ui.screen.TrackScreenViewEvent
import com.apero.sample.ui.theme.ColorBackground
import com.apero.sample.ui.theme.FontMedium
import com.apero.sample.ui.theme.FontRegular
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Created by KO Huyn.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingRoute(
    vm: OnboardViewModel,
    onNavigateToHome: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val listOnBoarding = LIST_ON_BOARDING
    LaunchedEffect(Unit) {
        while (isActive) {
            delay(TIMEOUT_PAGER)
            if (pagerState.currentPage < listOnBoarding.size - 1) {
                pagerState.scrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.scrollToPage(0)
            }
        }
    }
    TrackScreenViewEvent(screenName = "OnBoarding")
    OnBoardingScreen(
        onNavigateToHome = {
            vm.makeOpenedOnBoarding()
            onNavigateToHome()
        },
        listOnBoarding = listOnBoarding,
        pagerState = pagerState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    onNavigateToHome: () -> Unit,
    listOnBoarding: List<OnBoardingData> = LIST_ON_BOARDING,
    pagerState: PagerState = rememberPagerState()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        HorizontalPager(
            modifier = Modifier.align(Alignment.BottomCenter),
            pageCount = listOnBoarding.size,
            state = pagerState
        ) { index ->
            val data = listOnBoarding[index]
            OnBoardingPagerItem(data)
        }

        OnBoardingBottomAction(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                ),
            onNavigateToHome = onNavigateToHome
        )
    }
}

@Composable
private fun OnBoardingPagerItem(data: OnBoardingData) {
    val painter = painterResource(id = data.image)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 1f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0f)
                        )
                    )
                )
                .statusBarsPadding()
                .padding(bottom = 100.dp, top = 8.dp)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = stringResource(id = data.title),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontMedium,
                    fontWeight = FontWeight(600),
                    color = Color.White
                ),
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = data.content),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontRegular,
                    fontWeight = FontWeight(400),
                    color = Color.White.copy(alpha = 0.8f)
                ),
            )
        }
    }
}

@Composable
private fun OnBoardingBottomAction(modifier: Modifier, onNavigateToHome: () -> Unit) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(onClick = onNavigateToHome) {
                Text(
                    modifier = Modifier.padding(end = ButtonDefaults.IconSpacing),
                    text = stringResource(id = R.string.explore_now),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontMedium,
                        fontWeight = FontWeight(600),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_skip),
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

private val LIST_ON_BOARDING = listOf(
    OnBoardingData(
        R.string.title_onboard_1,
        R.string.content_onboard_1,
        R.drawable.img_onboard_1
    ),
    OnBoardingData(
        R.string.title_onboard_2,
        R.string.content_onboard_2,
        R.drawable.img_onboard_2
    ),
    OnBoardingData(
        R.string.title_onboard_3,
        R.string.content_onboard_3,
        R.drawable.img_onboard_3
    ),
)

private const val TIMEOUT_PAGER = 3000L

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun OnBoardingPreview() {
    OnBoardingScreen(onNavigateToHome = {})
}
