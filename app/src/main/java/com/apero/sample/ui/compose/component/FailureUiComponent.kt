package com.apero.sample.ui.compose.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.sample.data.state.FailureUi
import com.apero.sample.ui.compose.theme.ColorTextPrimary
import com.apero.sample.ui.compose.theme.FontRegular

/**
 * Created by KO Huyn.
 */

@Preview
@Composable
fun NoDataComponentPreview() {
    FailureUiComponent(
        modifier = Modifier,
        imageThumb = null,
        textHeader = "Nothing here...",
        textBody = "Seem like you havent created any  yet.\nCreate one now",
        textButton = "Create AI ",
        onClickAction = {}
    )
}

@Preview
@Composable
fun NoDataComponentErrorUiPreview() {
    FailureUiComponent(
        modifier = Modifier,
        data = FailureUi.mock()
    )
}

@Composable
fun FailureUiComponent(
    modifier: Modifier = Modifier,
    data: FailureUi,
    onClickAction: () -> Unit = {}
) {
    val context = LocalContext.current
    FailureUiComponent(
        modifier = modifier,
        imageThumb = if (data.imageRes != null) painterResource(id = data.imageRes) else null,
        textHeader = data.label?.getBy(context),
        textBody = data.describe?.getBy(context),
        textButton = data.buttonText?.getBy(context),
        buttonVisible = data.buttonVisible,
        onClickAction = onClickAction
    )
}

@Composable
fun FailureUiComponent(
    modifier: Modifier = Modifier,
    imageThumb: Painter?,
    textHeader: String?,
    textHeaderColor: Color = ColorTextPrimary,
    textBody: String?,
    textBodyColor: Color = ColorTextPrimary,
    textButton: String?,
    onClickAction: () -> Unit,
    buttonVisible: Boolean = true
) {
    Column(modifier = modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        if (imageThumb != null) {
            Image(
                modifier = Modifier.size(150.dp),
                painter = imageThumb,
                contentDescription = null,
            )
        }
        if (textHeader != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textHeader,
                textAlign = TextAlign.Center,
                fontFamily = FontRegular,
                color = textHeaderColor
            )
        }
        if (textBody != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = textBody,
                textAlign = TextAlign.Center,
                fontFamily = FontRegular,
                color = textBodyColor
            )
        }
        if (buttonVisible && textButton != null) {
            Spacer(modifier = Modifier.height(8.dp))
            ButtonDefault(
                shape = RoundedCornerShape(100.dp),
                buttonText = textButton,
                textColor = Color.White,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                onClickAction()
            }
        }
    }
}