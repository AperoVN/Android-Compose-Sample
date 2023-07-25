package com.apero.sample.ui.screen.setting.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.apero.sample.data.model.Language
import com.apero.sample.ui.component.ToolbarDefault
import com.apero.sample.ui.component.ToolbarTitleAlignment
import com.apero.sample.ui.screen.TrackScreenViewEvent

@Composable
fun LanguageRouter(
    viewModel: LanguageViewModel,
    onNavigateUp: () -> Unit,
    onNavigateNext: () -> Unit
) {
    val languageUiState by viewModel.languageUiState.collectAsState()
    val isLanguageFirstOpen by viewModel.needOpenLanguage.collectAsState(initial = null)
    if (isLanguageFirstOpen != null) {
        val screenName = if (isLanguageFirstOpen != false) "LanguageFirstOpen" else "Language"
        TrackScreenViewEvent(screenName = screenName)
    }
    LanguageScreen(
        languageUiState = languageUiState,
        onNavigateUp = onNavigateUp,
        onLanguageSelected = viewModel::setLanguageSelected,
        onSaveLanguage = {
            viewModel.saveCurrentLanguage()
            onNavigateNext()
        }
    )
}

@Composable
fun LanguageScreen(
    languageUiState: LanguageUiState,
    onNavigateUp: () -> Unit,
    onLanguageSelected: (itemSelected: Language) -> Unit,
    onSaveLanguage: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ToolbarDefault(
            layoutTitle = {
                Text(
                    text = stringResource(id = R.string.setting_language),
                    fontSize = 20.sp,
                    color = Color.White
                )
            },
            onClickNegative = onNavigateUp,
            titleAlignment = ToolbarTitleAlignment.START,
            layoutPositive = {
                Icon(
                    modifier = Modifier
                        .size(24.dp + (8.dp * 2))
                        .padding(8.dp)
                        .clickable {
                            onSaveLanguage()
                        },
                    painter = painterResource(id = R.drawable.ic_language_done),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            languageUiState.listLanguage.forEach { item ->
                LanguageItem(
                    item = item,
                    isSelected = item == languageUiState.languageSelected,
                    onClick = onLanguageSelected
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LanguageItem(
    modifier: Modifier = Modifier,
    item: Language,
    isSelected: Boolean,
    onClick: (item: Language) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable { onClick(item) }
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(size = 8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        Row(
            modifier = Modifier
                .padding(8.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                modifier = Modifier
                    .size(28.dp + (8.dp * 2))
                    .padding(8.dp),
                painter = painterResource(id = item.flag),
                contentDescription = null
            )
            Text(
                text = item.countryName.getBy(context),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color.White
                )
            )
        }
        Row(
            modifier = Modifier.padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp + (8.dp * 2))
                    .padding(8.dp),
                painter = painterResource(
                    id =
                    if (isSelected) R.drawable.ic_language_selected
                    else R.drawable.ic_language_not_selected
                ),
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
fun SettingScreenPreview() {
    LanguageScreen(
        LanguageUiState(
            Language.values().toList(),
            Language.EN,
            Language.EN
        ), {}, {}, {})
}