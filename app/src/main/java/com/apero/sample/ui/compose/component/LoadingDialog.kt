package com.apero.sample.ui.compose.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.apero.sample.R
import com.apero.sample.ui.compose.theme.DefaultTextStyleTitle

@Preview(showBackground = false)
@Composable
fun LoadingPreview() {
    LoadingDialog(onDismiss = {})
}

@Composable
fun LoadingDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss.invoke() }) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 1f,
            restartOnPlay = false
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(
                modifier = Modifier
                    .size(80.dp),
                composition = composition,
                progress = { progress })

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.loading),
                style = DefaultTextStyleTitle.copy(fontSize = 16.sp)
            )
        }
    }
}