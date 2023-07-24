package com.apero.sample.ui.screen.selectphoto

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.apero.sample.R
import com.apero.sample.data.model.AlbumMedia
import com.apero.sample.data.model.MediaModel
import com.apero.sample.ui.component.DropDownModel
import com.apero.sample.ui.component.FailureUiComponent
import com.apero.sample.ui.component.ImageLoader
import com.apero.sample.ui.component.LoadingDialog
import com.apero.sample.ui.component.SpinnerDefault
import com.apero.sample.ui.component.ToolbarDefault
import com.apero.sample.ui.theme.DefaultTextStyleContent
import com.apero.sample.ui.theme.DefaultTextStyleTitle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImageSelectorRoute(
    vm: ImageSelectorViewModel,
    onNavigateUp: () -> Unit,
    onNavigateToPreview: suspend (data: MediaModel) -> Unit,
    onOpenSettingApp: () -> Unit
) {
    val listImageSample: List<Painter> = listOf(
        painterResource(R.drawable.img_sample_photo_1),
        painterResource(R.drawable.img_sample_photo_2),
        painterResource(R.drawable.img_sample_photo_3),
        painterResource(R.drawable.img_sample_photo_4)
    )

    val mediaPermissionsState = rememberMultiplePermissionsState(
        listOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            },
        )
    )
    LaunchedEffect(mediaPermissionsState.allPermissionsGranted) {
        vm.updatePermission(mediaPermissionsState.allPermissionsGranted)
    }
    LaunchedEffect(Unit) {
        vm.selectMediaState.collect { media ->
            onNavigateToPreview(media)
        }
    }
    val uiState by vm.imageSelectorUiState.collectAsState()

    if (uiState.isLoading) {
        LoadingDialog(onDismiss = {})
    }

    ImageSelectorScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToPreview = {
            vm.selectMedia(it)
        },
        listImageSample = listImageSample,
        uiState = uiState,
        onSelectMedia = {
            vm.updateMediaSelected(it)
        },
        onSelectAlbum = vm::updateAlbumSelected,
        onRequestPermission = {
            if (uiState.isFirstTimeAskPermission || mediaPermissionsState.shouldShowRationale) {
                mediaPermissionsState.launchMultiplePermissionRequest()
                if (uiState.isFirstTimeAskPermission) {
                    vm.updateFirstTimeRequestPermission()
                }
            } else {
                onOpenSettingApp()
            }
        }
    )
}

@Composable
fun ImageSelectorScreen(
    onNavigateUp: () -> Unit,
    onNavigateToPreview: (MediaModel?) -> Unit,
    listImageSample: List<Painter>,
    uiState: ImageSelectorUiState,
    onSelectAlbum: (album: AlbumMedia) -> Unit,
    onSelectMedia: (MediaModel) -> Unit,
    onRequestPermission: () -> Unit
) {
    val spanCountSample = 4
    val spanCountYourPhoto = 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ToolbarDefault(
            iconNegative = painterResource(id = R.drawable.ic_close),
            onClickNegative = onNavigateUp,
            layoutTitle = {
                SpinnerDefault(
                    list = uiState.listAlbum.map { DropDownModel(it.name, it) },
                    onItemSelectedListener = onSelectAlbum
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .widthIn(0.dp, 200.dp),
                        text = uiState.albumSelected?.name
                            ?: stringResource(id = R.string.label_all),
                        style = DefaultTextStyleTitle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            },
            layoutPositive = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp + (8.dp * 2))
                        .padding(8.dp)
                        .clickable { onNavigateToPreview(uiState.mediaSelected) },
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))
        ImageSelectorSample(
            modifier = Modifier.padding(horizontal = 12.dp),
            listImageSample = listImageSample,
            spanCount = spanCountSample
        )
        Spacer(modifier = Modifier.height(12.dp))

        ImageSelectorList(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            hasPermission = uiState.hasPermission,
            album = uiState.albumSelected,
            mediaSelected = uiState.mediaSelected,
            onSelectMedia = {
                onSelectMedia(it)
            },
            spanCount = spanCountYourPhoto,
            requestPermission = onRequestPermission
        )
    }
}

@Composable
fun ImageSelectorList(
    modifier: Modifier = Modifier,
    album: AlbumMedia?,
    hasPermission: Boolean,
    mediaSelected: MediaModel?,
    onSelectMedia: (MediaModel) -> Unit,
    spanCount: Int,
    requestPermission: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp),
            text = stringResource(R.string.select_photo_your_photo),
            style = DefaultTextStyleTitle
        )

        if (hasPermission) {
            if (album == null || album.listMedia.isEmpty()) {
                FailureUiComponent(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterHorizontally),
                    imageThumb = painterResource(id = R.drawable.img_empty_box),
                    textHeader = stringResource(id = R.string.error_album_empty),
                    textBody = null,
                    textButton = null,
                    onClickAction = {},
                    buttonVisible = false
                )
            } else {
                val listMedia = album.listMedia
                LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(spanCount)) {
                    items(listMedia.size) { index ->
                        val isSelected =
                            if (mediaSelected == null) false else mediaSelected.id == listMedia[index].id
                        Box(modifier = Modifier
                            .aspectRatio(1f)
                            .padding(
                                6.dp
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSelectMedia(listMedia[index]) }) {
                            ImageLoader(modifier = Modifier.fillMaxSize()) {
                                data(listMedia[index].path)
                            }
                            Icon(
                                painterResource(id = if (isSelected) R.drawable.ic_cb_selected else R.drawable.ic_cb_unselect),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(24.dp)
                                    .align(Alignment.TopEnd)
                            )
                        }
                    }
                }
            }
        } else {
            FailureUiComponent(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally),
                imageThumb = null,
                textHeader = stringResource(R.string.select_photo_request_permission),
                textBody = null,
                textButton = stringResource(R.string.select_photo_request_permission_open_setting),
                onClickAction = requestPermission,
                buttonVisible = true
            )
        }
    }
}

@Composable
fun ImageSelectorSample(modifier: Modifier, listImageSample: List<Painter>, spanCount: Int) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.select_photo_sample_photo),
            style = DefaultTextStyleTitle
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = stringResource(R.string.select_photo_sample_content),
            style = DefaultTextStyleContent
        )
        Spacer(modifier = modifier.height(8.dp))
        LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(spanCount)) {
            items(listImageSample.size) { index ->
                ConstraintLayout(
                    modifier = Modifier
                        .padding(4.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    val (imageSample, textSample) = createRefs()

                    Image(
                        modifier = Modifier
                            .constrainAs(imageSample) {}
                            .fillMaxWidth(),
                        painter = listImageSample[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = stringResource(R.string.select_photo_sample),
                        Modifier
                            .fillMaxWidth()
                            .constrainAs(textSample) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(8.dp),
                        style = DefaultTextStyleContent,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun ImageSelectorPreview() {
    ImageSelectorScreen(onNavigateUp = {},
        onNavigateToPreview = {},
        listImageSample = listOf(
            painterResource(R.drawable.img_sample_photo_1),
            painterResource(R.drawable.img_sample_photo_2),
            painterResource(R.drawable.img_sample_photo_3),
            painterResource(R.drawable.img_sample_photo_4)
        ),
        uiState = ImageSelectorUiState(hasPermission = true, albumSelected = AlbumMedia.mock()),
        onSelectMedia = {},
        onSelectAlbum = {},
        onRequestPermission = {})
}

@Preview
@Composable
fun ImageSelectorNotPermissionPreview() {
    ImageSelectorScreen(onNavigateUp = {},
        onNavigateToPreview = {},
        listImageSample = listOf(
            painterResource(R.drawable.img_sample_photo_1),
            painterResource(R.drawable.img_sample_photo_2),
            painterResource(R.drawable.img_sample_photo_3),
            painterResource(R.drawable.img_sample_photo_4)
        ), uiState = ImageSelectorUiState(),
        onSelectAlbum = {},
        onSelectMedia = {},
        onRequestPermission = {})
}

@Preview
@Composable
fun ImageSelectorEmptyPicturePreview() {
    ImageSelectorScreen(onNavigateUp = {},
        onNavigateToPreview = {},
        listImageSample = listOf(
            painterResource(R.drawable.img_sample_photo_1),
            painterResource(R.drawable.img_sample_photo_2),
            painterResource(R.drawable.img_sample_photo_3),
            painterResource(R.drawable.img_sample_photo_4)
        ),
        uiState = ImageSelectorUiState(hasPermission = true),
        onSelectMedia = {},
        onSelectAlbum = {},
        onRequestPermission = {})
}