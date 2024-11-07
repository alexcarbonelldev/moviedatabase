package com.bd.bd.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.bd.R
import com.bd.domain.model.Media
import com.bd.domain.model.MediaType
import com.bd.domain.model.getType
import com.bd.ui.design_system.component.CardItemUiComponent
import com.bd.ui.mvi.ViewEventObserver

private enum class HomeSection {
    TRENDING_MOVIES,
    TRENDING_TV_SHOWS
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navToDetail: (id: String, mediaType: MediaType) -> Unit
) {
    viewModel.ViewEventObserver { event ->
        when (event) {
            is HomeViewEvent.NavToDetail -> navToDetail(event.mediaId, event.mediaType)
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    HomeScreen(
        viewState,
        viewModel::onViewAction
    )
}

@Composable
private fun HomeScreen(
    viewState: HomeViewState,
    onViewAction: (HomeViewAction) -> Unit
) {
    Box {
        when (viewState) {
            HomeViewState.Error -> Unit // TODO: Create Error screen
            HomeViewState.Loading -> Loading()
            is HomeViewState.Content -> Success(
                viewState = viewState,
                onViewAction = onViewAction
            )
        }
    }
}

@Composable
private fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
private fun Success(
    viewState: HomeViewState.Content,
    onViewAction: (HomeViewAction) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.trending),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.displaySmall
        )
        Section(
            section = HomeSection.TRENDING_MOVIES,
            mediaList = viewState.trendingMovies,
            onRecommendedMediaClick = { id, mediaType ->
                onViewAction(HomeViewAction.OnMediaClicked(id, mediaType))
            }
        )
        Section(
            section = HomeSection.TRENDING_TV_SHOWS,
            mediaList = viewState.trendingTvShows,
            onRecommendedMediaClick = { id, mediaType ->
                onViewAction(HomeViewAction.OnMediaClicked(id, mediaType))
            }
        )
    }
}

@Composable
private fun Section(
    section: HomeSection,
    mediaList: List<Media>,
    onRecommendedMediaClick: (id: String, mediaType: MediaType) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        val titleResId = when (section) {
            HomeSection.TRENDING_MOVIES -> R.string.movies
            HomeSection.TRENDING_TV_SHOWS -> R.string.tv_shows
        }
        Text(
            text = stringResource(titleResId),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge
        )
        LazyRow {
            itemsIndexed(
                items = mediaList,
                key = { _, media -> media.id },
                itemContent = { index, media ->
                    Spacer(Modifier.width(16.dp))

                    CardItemUiComponent(
                        imageUrl = media.imageUrl,
                        rating = media.rating,
                        imageHeight = 200.dp,
                        imageWidth = 140.dp,
                        onClick = { onRecommendedMediaClick(media.id, media.getType()) }
                    )

                    if (index == mediaList.lastIndex) {
                        Spacer(Modifier.width(16.dp))
                    }
                }
            )
        }
    }
}
