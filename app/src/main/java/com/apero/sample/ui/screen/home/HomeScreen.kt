package com.apero.sample.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apero.sample.R
import com.apero.sample.data.model.ImageSize
import com.apero.sample.data.model.Movie
import com.apero.sample.data.state.PagingState
import com.apero.sample.ui.component.FailureUiComponent
import com.apero.sample.ui.component.ImageLoader
import com.apero.sample.ui.component.ToolbarDefault
import com.apero.sample.ui.component.ToolbarTitleAlignment
import com.apero.sample.ui.theme.ColorPrimary

/**
 * Created by KO Huyn on 20/07/2023.
 */

@Composable
fun HomeRoute(
    vm: HomeViewModel,
    onClickSetting: () -> Unit,
    onClickGallery: () -> Unit,
    onClickMovieDetail: (movie: Movie) -> Unit
) {
    val uiState by vm.uiState.collectAsState()
    val scrollState = rememberLazyGridState()
    val canLoadPage by remember {
        derivedStateOf {
            val canLoadPage = vm.uiState.value.pagingMovie.canLoadPage
            val lastIndexVisible = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItemCount = scrollState.layoutInfo.totalItemsCount
            canLoadPage && lastIndexVisible in totalItemCount - (uiState.spanCount * 2)..totalItemCount
        }
    }
    LaunchedEffect(canLoadPage) {
        if (canLoadPage) {
            vm.loadMore()
        }
    }
    HomeScreen(
        uiState = uiState,
        scrollState = scrollState,
        onClickMovie = onClickMovieDetail,
        onClickSetting = onClickSetting,
        onClickGallery = onClickGallery
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    scrollState: LazyGridState = rememberLazyGridState(),
    onClickMovie: (Movie) -> Unit,
    onClickSetting: () -> Unit,
    onClickGallery: () -> Unit,
) {
    val movies = uiState.listMovie
    val pagingState = uiState.pagingMovie.pagingState
    Column(modifier = Modifier.fillMaxSize()) {
        ToolbarDefault(
            modifier = Modifier.background(ColorPrimary),
            iconNegative = null,
            onClickNegative = {},
            titleAlignment = ToolbarTitleAlignment.START,
            layoutTitle = {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }, layoutPositive = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp + (8.dp * 2))
                        .padding(8.dp)
                        .clickable(role = Role.Image) { onClickGallery() },
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_setting),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp + (8.dp * 2))
                        .padding(8.dp)
                        .clickable(role = Role.Image) { onClickSetting() },
                )
            }
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(uiState.spanCount),
                contentPadding = PaddingValues(4.dp),
                state = scrollState
            ) {
                items(items = movies, key = { it.id }) { movie ->
                    MovieItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        movie = movie,
                        onItemClick = onClickMovie,
                    )
                }
                item(
                    span = { GridItemSpan(uiState.spanCount) }
                ) {
                    when (pagingState) {
                        is PagingState.LoadState.LoadMore -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(40.dp)
                                )
                            }
                        }

                        is PagingState.Failure.PaginationExhaust -> {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 16.dp)
                            ) {
                                FailureUiComponent(data = pagingState.failureState.toUiState())
                            }
                        }

                        else -> Unit
                    }
                }
            }
            when (pagingState) {
                is PagingState.LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                is PagingState.Failure.Error -> {
                    FailureUiComponent(data = pagingState.failureState.toUiState())
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier,
    movie: Movie,
    onItemClick: (Movie) -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(role = Role.Image, onClick = { onItemClick(movie) })
    ) {
        ImageLoader(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9 / 16f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White),
            url = movie.posterPath?.create(ImageSize.PosterSize.W154)
        )

        Text(
            modifier = Modifier,
            textAlign = TextAlign.Start,
            text = movie.title ?: "",
            style = TextStyle(color = Color.White, fontSize = 14.sp)
        )
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Start,
            text = movie.releaseDate ?: "",
            style = TextStyle(color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        )
    }
}

@Preview(widthDp = 200)
@Composable
fun MovieItemPreview() {
    MovieItem(modifier = Modifier, movie = Movie.mock(), onItemClick = {})
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            uiState = HomeUiState.mock(),
            onClickMovie = {},
            onClickSetting = {},
            onClickGallery = {}
        )
    }
}