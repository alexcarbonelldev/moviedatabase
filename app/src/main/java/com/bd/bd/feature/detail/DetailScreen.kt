package com.bd.bd.feature.detail

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import com.bd.domain.model.ContentType
import com.bd.ui.R
import com.bd.ui.common.toDp
import com.bd.ui.designsystem.component.AsyncImageUiComponent
import com.bd.ui.designsystem.component.CardItemUiComponent
import com.bd.ui.designsystem.component.CustomTopBarUiComponent
import com.bd.ui.designsystem.component.ErrorStateUiComponent
import com.bd.ui.designsystem.component.LoadingStateUiComponent
import com.bd.ui.designsystem.component.rating.RatingUiComponent
import com.bd.ui.mvi.ViewEventObserver

private const val HEADER_BACKGROUND_HEIGHT = 300
private const val SCROLL_HEIGHT = 160

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navToMovieDetail: (id: String) -> Unit,
    navToTvShowDetail: (id: String) -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    viewModel.ViewEventObserver { event ->
        when (event) {
            is DetailViewEvent.NavToMovieDetail -> navToMovieDetail(event.id)
            is DetailViewEvent.NavToTvShowDetail -> navToTvShowDetail(event.id)
        }
    }

    Crossfade(
        targetState = viewState,
        label = "detailScreenAnimation"
    ) { state ->
        when (state) {
            is DetailViewState.Content -> DetailContent(
                state,
                onBackClick = onBackClick,
                onRecommendedMediaClick = { id, mediaType ->
                    viewModel.onViewAction(DetailViewAction.OnRecommendedMediaClick(id, mediaType))
                }
            )

            is DetailViewState.Error -> ErrorStateUiComponent(
                onRetryClick = { viewModel.onViewAction(DetailViewAction.OnRetryClick) }
            )

            is DetailViewState.Loading -> LoadingStateUiComponent()
        }
    }
}

@Composable
private fun DetailContent(
    content: DetailViewState.Content,
    onBackClick: () -> Unit,
    onRecommendedMediaClick: (id: String, mediaType: ContentType.Media) -> Unit
) {
    Box {
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
                Column(modifier = Modifier.padding(vertical = 16.dp)) {
                    GenresRow(content.genres)
                    Text(
                        text = content.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = content.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Recommendations(
                        content.recommendations,
                        onRecommendedMovieClick = onRecommendedMediaClick
                    )
                }
            }
        }

        val topBarAlpha: Float = remember(scrollValueDp) {
            (1 - (scrollValueDp / SCROLL_HEIGHT)).takeIf { it >= 0f } ?: 0f
        }
        if (topBarAlpha > 0) {
            CustomTopBarUiComponent(
                modifier = Modifier
                    .alpha(topBarAlpha),
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun Recommendations(
    recommendations: List<RecommendedMediaUiModel>,
    onRecommendedMovieClick: (id: String, mediaType: ContentType.Media) -> Unit
) {
    if (recommendations.isEmpty()) return

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = stringResource(R.string.related_content),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow {
            itemsIndexed(
                items = recommendations,
                key = { _, movie -> movie.id },
                itemContent = { index, movie ->
                    Spacer(Modifier.width(16.dp))

                    CardItemUiComponent(
                        imageUrl = movie.imageUrl,
                        rating = movie.rating,
                        imageHeight = 140.dp,
                        imageWidth = 100.dp,
                        onClick = { onRecommendedMovieClick(movie.id, movie.mediaType) }
                    )

                    if (index == recommendations.lastIndex) {
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
        itemsIndexed(genres) { i, genre ->
            Spacer(modifier = Modifier.width(16.dp))
            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            )

            if (i == genres.lastIndex) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}
