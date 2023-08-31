package com.apero.sample.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apero.sample.ui.compose.theme.ColorTextOnPrimary
import com.apero.sample.ui.compose.theme.ColorsGradient
import com.apero.sample.ui.compose.theme.FontMedium

/**
 * Created by KO Huyn on 27/06/2023.
 */

@Composable
fun ButtonDefault(
    buttonText: String,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(16.dp),
    shape: RoundedCornerShape = RoundedCornerShape(24.dp),
    enable: Boolean = true,
    iconLeft: Painter? = null,
    textColor: Color = ColorTextOnPrimary,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight(500),
    fontFamily: FontFamily = FontMedium,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .alpha(if (enable) 1f else 0.5f)
            .padding(paddingValues = padding)
            .clip(shape)
            .background(Brush.horizontalGradient(ColorsGradient))
            .clickable(enabled = enable, onClick = onClick),
    ) {
        if (iconLeft != null) {
            Image(
                painter = iconLeft,
                contentDescription = null,
                modifier = Modifier.padding(end = 10.dp)
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(padding),
            text = buttonText,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                color = textColor
            ),
        )
    }
}

@Preview
@Composable
fun ButtonDefaultPreview() {
    ButtonDefault(modifier = Modifier.fillMaxWidth(), buttonText = "Create") {}
}

@Preview
@Composable
fun ButtonDefaultDisablePreview() {
    ButtonDefault(modifier = Modifier.fillMaxWidth(), buttonText = "Create", enable = false) {}
}