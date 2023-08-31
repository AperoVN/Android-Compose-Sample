package com.apero.sample.ui.compose.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apero.sample.R
import com.apero.sample.data.db.model.MovieEntity
import com.apero.sample.ui.compose.component.FailureUiComponent
import com.apero.sample.ui.compose.component.ImageLoader
import com.apero.sample.ui.compose.component.ToolbarDefault
import com.apero.sample.ui.compose.theme.ColorBackground
import com.apero.sample.ui.compose.theme.FontMedium
import com.apero.sample.ui.viewmodel.HistoryUIState
import com.apero.sample.ui.viewmodel.HistoryViewModel
import kotlinx.coroutines.launch

@Composable
fun HistoryRoute(
    viewModel: HistoryViewModel,
    onNavigateUp: () -> Unit
) {
    val context = LocalContext.current
    val historyUiState by viewModel.historyState.collectAsState()
    HistoryScreen(
        state = historyUiState,
        onNavigateUp = onNavigateUp
    )
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    HistoryScreen(HistoryUIState(), {})
}

private const val SPAN_COUNT = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HistoryScreen(
    state: HistoryUIState,
    onNavigateUp: () -> Unit,
) {
    var showMorePopup by remember { mutableStateOf(false) }
    var showDeletePopup by remember { mutableStateOf(false) }
    var showPreviewPopup by remember { mutableStateOf(false) }
    var showLoadingPopup by remember { mutableStateOf(false) }
    var itemSelected by remember { mutableStateOf<MovieEntity?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(horizontal = 16.dp)
    ) {
        ToolbarDefault(
            modifier = Modifier,
            layoutTitle = {
                Text(
                    text = stringResource(R.string.gallery),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontMedium
                )
            },
            onClickNegative = onNavigateUp,
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                state.listHistory.isEmpty() -> {
                    FailureUiComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 40.dp, 0.dp, 0.dp),
                        imageThumb = painterResource(R.drawable.img_empty_box),
                        textHeader = stringResource(R.string.home_no_data_title),
                        textBody = stringResource(R.string.home_no_data_content),
                        textButton = stringResource(R.string.label_create_ai_qr),
                        onClickAction = { }
                    )
                }

                else -> {
                    val items = state.listHistory
                    val coroutineScope = rememberCoroutineScope()
                    val bottomSheetState =
                        rememberModalBottomSheetState(skipPartiallyExpanded = true)

                    ListHistory(items = items,
                        onItemClick = {
                            itemSelected = it
                            showPreviewPopup = true
                        },
                        onMoreAction = {
                            coroutineScope.launch {
                                showMorePopup = true
                                itemSelected = it
                            }
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemHistoryPreview() {
    ListHistory(items = List(10) { MovieEntity.mock() }, onItemClick = {}, onMoreAction = {})
}

@Composable
private fun ListHistory(
    items: List<MovieEntity>,
    onItemClick: (MovieEntity) -> Unit,
    onMoreAction: (MovieEntity) -> Unit
) {
    LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(SPAN_COUNT)) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemClick(items[index]) },
                contentAlignment = Alignment.TopEnd
            ) {
                ImageLoader(
                    modifier = Modifier
                        .aspectRatio(0.6f)
                        .padding(
                            end = if ((index + 1) % SPAN_COUNT == 0) 0.dp else 12.dp, bottom = 12.dp
                        )
                        .clickable(role = Role.Image) {
                            onItemClick(items[index])
                        }
                        .clip(RoundedCornerShape(12.dp)),
                    url = items[index].posterPath?:""
                )
                Icon(
                    modifier = Modifier
                        .size(24.dp + if ((index + 1) % SPAN_COUNT == 0) (8.dp * 2) else (20.dp * 2))
                        .clickable { onMoreAction(items[index]) }
                        .padding(
                            start = if ((index + 1) % SPAN_COUNT == 0) 8.dp else 20.dp,
                            end = if ((index + 1) % SPAN_COUNT == 0) 8.dp else 20.dp,
                            top = 8.dp,
                            bottom = if ((index + 1) % SPAN_COUNT == 0) 8.dp else 32.dp
                        ),
                    painter = painterResource(id = R.drawable.ic_more_history),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }

        }
    }
}