package com.apero.sample.ui.compose.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apero.sample.R
import com.apero.sample.ui.compose.component.ToolbarDefault
import com.apero.sample.ui.compose.component.ToolbarTitleAlignment
import com.apero.sample.ui.compose.theme.ColorBackground
import com.apero.sample.ui.compose.theme.ColorTextPrimary
import com.apero.sample.ui.compose.theme.ColorTextSecondary
import com.apero.sample.ui.compose.theme.FontMedium
import com.apero.sample.ui.compose.theme.FontRegular
import com.apero.sample.ui.compose.screen.TrackScreenViewEvent
import com.apero.sample.ui.viewmodel.SettingItem
import com.apero.sample.ui.viewmodel.SettingUiState
import com.apero.sample.ui.viewmodel.SettingViewModel
import com.apero.sample.ui.viewmodel.settingList

@Composable
fun SettingRoute(
    viewModel: SettingViewModel,
    onNavigationUp: () -> Unit,
    onNavigateToLanguage: () -> Unit
) {
    val settingUiState by viewModel.settingUiState.collectAsState()
    TrackScreenViewEvent(screenName = "Setting")
    SettingScreen(
        settingUiState = settingUiState,
        onNavigationUp = onNavigationUp,
        onNavigateToLanguage = onNavigateToLanguage
    )
}

@Composable
fun SettingScreen(
    settingUiState: SettingUiState,
    onNavigationUp: () -> Unit,
    onNavigateToLanguage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
    ) {
        ToolbarDefault(
            layoutTitle = {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.settings),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = ColorTextPrimary,
                        fontWeight = FontWeight(500),
                        fontFamily = FontMedium
                    )
                )
            },
            onClickNegative = { onNavigationUp() },
            titleAlignment = ToolbarTitleAlignment.START
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxSize()
        ) {
            settingUiState.listSetting.forEach { item ->
                ItemSettingScreen(
                    item = item,
                    onClick = {
                        when (item) {
                            is SettingItem.SettingLanguage -> {
                                onNavigateToLanguage()
                            }

                            is SettingItem.PrivacyPolicy -> {
                            }

                            is SettingItem.TermsOfService -> {
                            }

                            is SettingItem.ShareApp -> {

                            }

                            is SettingItem.Version -> {
                                // do nothing
                            }
                        }
                    })
            }
        }
    }
}

@Composable
fun ItemSettingScreen(
    modifier: Modifier = Modifier,
    item: SettingItem,
    tint: Color = Color.White,
    onClick: (item: SettingItem) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick(item) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp),
                painter = painterResource(id = item.iconResStart),
                contentDescription = null,
                tint = tint
            )
            Text(
                text = stringResource(id = item.titleRes),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = tint,
                    fontFamily = FontRegular
                )
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (item.value != null) {
                val context = LocalContext.current
                Text(
                    modifier = Modifier.padding(end = if (item.iconResEnd != null) 0.dp else 8.dp),
                    text = item.value.getBy(context),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = ColorTextSecondary,
                        fontFamily = FontRegular
                    ),
                )
            }
            if (item.iconResEnd != null) {
                Icon(
                    modifier = Modifier
                        .size(24.dp + (8.dp * 2))
                        .padding(8.dp),
                    painter = painterResource(id = item.iconResEnd),
                    contentDescription = null,
                    tint = tint,
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(SettingUiState(listSetting = settingList), {}, {})
}



