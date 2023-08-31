package com.apero.sample.ui.compose.component

import android.graphics.Bitmap
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apero.sample.R
import com.apero.sample.ui.compose.theme.ColorPrimary
import com.apero.sample.ui.compose.theme.ColorsGradient
import java.io.File

/**
 * Created by KO Huyn on 28/06/2023.
 */

@Preview
@Composable
private fun ImageLoaderPreview() {
    ImageLoader(
        modifier = Modifier.size(40.dp),
        drawable = R.drawable.ic_launcher_foreground
    )
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    file: File,
    request: ImageRequest.Builder.() -> ImageRequest.Builder = { this }
) {
    ImageLoader(modifier = modifier, request = {
        request()
        data(file)
    })
}


@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    bitmap: Bitmap,
    request: ImageRequest.Builder.() -> ImageRequest.Builder = { this }
) {
    ImageLoader(modifier = modifier, request = {
        request()
        data(bitmap)
    })
}


@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int,
    request: ImageRequest.Builder.() -> ImageRequest.Builder = { this }
) {
    ImageLoader(modifier = modifier, request = {
        request()
        data(drawable)
    })
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    uri: Uri,
    request: ImageRequest.Builder.() -> ImageRequest.Builder = { this }
) {
    ImageLoader(modifier = modifier, request = {
        request()
        data(uri)
    })
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String?,
    request: ImageRequest.Builder.() -> ImageRequest.Builder = { this }
) {
    ImageLoader(modifier = modifier, request = {
        request()
        data(url)
    })
}

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    request: ImageRequest.Builder.() -> ImageRequest.Builder
) {
    val model = request(ImageRequest.Builder(LocalContext.current).crossfade(true)).build()
    var imageState: ImageLoaderState by remember(model) {
        mutableStateOf(ImageLoaderState.NONE)
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth(),
            onLoading = {
                imageState = ImageLoaderState.LOADING
            },
            onSuccess = {
                imageState = ImageLoaderState.SUCCESS
            },
            onError = {
                imageState = ImageLoaderState.ERROR
            },
            contentScale = ContentScale.Crop,
            model = model,
            contentDescription = null, // decorative image
        )
        when (imageState) {
            ImageLoaderState.ERROR -> {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.error_could_load_image),
                    style = TextStyle(fontSize = 12.sp, color = Color.White),
                    textAlign = TextAlign.Center
                )
            }

            ImageLoaderState.LOADING -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(ColorsGradient)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ColorPrimary)
                }
            }

            else -> Unit
        }
    }
}

enum class ImageLoaderState {
    NONE, LOADING, SUCCESS, ERROR
}