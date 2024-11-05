package com.bd.bd.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.bd.R
import com.bd.ui.common.toDp
import com.bd.ui.design_system.component.AsyncImageUiComponent
import com.bd.ui.design_system.component.CardItemUiComponent
import com.bd.ui.design_system.component.CustomTopBarUiComponent
import com.bd.ui.design_system.component.RatingUiComponent
import com.bd.ui.mvi.ViewEventObserver

private const val HEADER_BACKGROUND_HEIGHT = 300

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navToDetail: (id: String) -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    viewModel.ViewEventObserver { event ->
        when (event) {
            is DetailViewEvent.NavToMovie -> navToDetail(event.id)
        }
    }

    when (state) {
        is DetailViewState.Content -> DetailContent(
            state as DetailViewState.Content,
            onBackClick = onBackClick,
            onRecommendedMovieClick = {
                viewModel.onViewAction(DetailViewAction.OnRecommendedMovieClicked(it))
            }
        )

        is DetailViewState.Error -> Unit // DetailError()
        is DetailViewState.Loading -> Unit //  DetailLoading()
    }
}

@Composable
private fun DetailContent(
    content: DetailViewState.Content,
    onBackClick: () -> Unit,
    onRecommendedMovieClick: (id: String) -> Unit
) {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            val scrollState = rememberScrollState()
            val scrollValuePx = remember(scrollState.value) { scrollState.value }
            val scrollValueDp = scrollValuePx.toDp()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.verticalScroll(scrollState)
                ) {
                    Header(scrollValueDp, content)
                    Column(modifier = Modifier.padding(16.dp)) {
                        GenresRow(content.genres)
                        Text(
                            text = content.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = content.description,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Recommendations(
                            content.recommendations,
                            onRecommendedMovieClick = onRecommendedMovieClick
                        )
                    }
                }
            }

            val topBarAlpha: Float = remember(scrollValueDp) { (1 - (scrollValueDp / 160)).takeIf { it >= 0f } ?: 0f }
            if (topBarAlpha > 0) {
                CustomTopBarUiComponent(
                    modifier = Modifier
                        .alpha(topBarAlpha),
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@Composable
fun Recommendations(
    movies: List<RecommendedMovieUiModel>,
    onRecommendedMovieClick: (id: String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.similar_movies),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 8.dp
                ),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow {
            itemsIndexed(
                items = movies,
                key = { _, movie -> movie.id },
                itemContent = { index, movie ->
                    CardItemUiComponent(
                        movie.imageUrl,
                        movie.rating,
                        imageHeight = 140.dp,
                        imageWidth = 100.dp,
                        onClick = { onRecommendedMovieClick(movie.id) }
                    )
                    if (index < movies.size - 1) {
                        Spacer(Modifier.width(16.dp))
                    }
                }
            )
        }
    }
}

@Composable
private fun Header(scrollValueDp: Float, content: DetailViewState.Content) {
    val headerAlpha = remember(scrollValueDp) {
        (1 - (scrollValueDp / HEADER_BACKGROUND_HEIGHT)).takeIf { it >= 0f } ?: 0f
    }
    Box(
        modifier = Modifier
            .height(HEADER_BACKGROUND_HEIGHT.dp)
            .fillMaxWidth()
            .alpha(headerAlpha)
    ) {
        HeaderBackground(content)
        AsyncImageUiComponent(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .height(200.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            imageUrl = content.imageUrl
        )
        RatingUiComponent(
            value = content.rating,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun HeaderBackground(
    content: DetailViewState.Content
) {
    Box(
        modifier = Modifier.height(HEADER_BACKGROUND_HEIGHT.dp)
    ) {
        AsyncImageUiComponent(
            modifier = Modifier
                .fillMaxWidth(),
            imageUrl = content.backgroundImageUrl
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(HEADER_BACKGROUND_HEIGHT.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color.Black, Color.Transparent),
                    ),
                )
        )
    }
}

@Composable
private fun GenresRow(genres: List<String>) {
    LazyRow(Modifier.fillMaxWidth()) {
        items(genres) { genre ->
            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
