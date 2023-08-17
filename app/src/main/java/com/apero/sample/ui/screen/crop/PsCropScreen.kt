package com.apero.sample.ui.screen.crop

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.apero.core.photocropper.CropScreen
import com.apero.core.photocropper.model.CropAspectRatio
import com.apero.core.photocropper.model.aspectRatioList
import com.apero.sample.ui.component.ToolbarDefault

@Composable
fun PsCropRoute(
    onNavigateUp: () -> Unit,
    onChangePhoto: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PsCropViewModel = hiltViewModel(),
) {
    val cropUiState = viewModel.cropUiState.collectAsState()
    var ratio: CropAspectRatio? by remember {
        mutableStateOf(null)
    }
    var successBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    if (successBitmap != null) {
        Dialog(onDismissRequest = { successBitmap = null }) {
            Image(
                bitmap = successBitmap!!,
                contentDescription = null,
            )
        }
    }

    PsCropScreen(
        imageCrop = cropUiState.value.dataInput!!,
        onNavigateUp = onNavigateUp,
        onChangePhoto = onChangePhoto,
        onCropStart = {
            /* no-op */
        },
        onCropSuccess = {
            successBitmap = it
        },
        onSelectRatio = {
            ratio = it
        },
        modifier = modifier
    )
}

private fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }
}

@Composable
fun PsCropScreen(
    imageCrop: Uri,
    onNavigateUp: () -> Unit,
    onChangePhoto: () -> Unit,
    onCropStart: () -> Unit,
    onCropSuccess: (imageCropped: ImageBitmap) -> Unit,
    onSelectRatio: (CropAspectRatio) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var snapCrop by remember { mutableStateOf(false) }
    val bitmap = remember(imageCrop, context) {
        getBitmapFromUri(imageCrop, context.contentResolver)
            .asImageBitmap()
    }
    CropScreen(
        imageCrop = bitmap,
        aspectRatioList = aspectRatioList,
        snapCrop = snapCrop,
        onChangePhoto = onChangePhoto,
        toolbarCrop = {
            ToolbarDefault(
                onClickNegative = onNavigateUp,
                iconNegative = rememberVectorPainter(image = Icons.Rounded.ArrowBack),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        buttonConfirmCrop = {
            Button(
                onClick = {
                    snapCrop = true
                }
            ) {
                Text(text = "Crop")
            }
        },
        onCropStart = onCropStart,
        onCropSuccess = onCropSuccess,
        onSelectRatio = onSelectRatio,
        modifier = modifier,
    )
}
