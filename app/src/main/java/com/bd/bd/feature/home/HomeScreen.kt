package com.bd.bd.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.bd.R
import com.bd.domain.model.Media
import com.bd.domain.model.MediaType
import com.bd.domain.model.getType
import com.bd.ui.design_system.component.CardItemUiComponent
import com.bd.ui.mvi.ViewEventObserver

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
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
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
    val gridState = rememberLazyGridState()

    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.popular_movies),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            items(
                count = viewState.topMovies.size,
                key = { viewState.topMovies[it].id }
            ) { i ->
                val media = viewState.topMovies[i]
                MediaItem(
                    media = media,
                    onClick = {
                        onViewAction(
                            HomeViewAction.OnMediaClicked(
                                media.id,
                                media.getType()
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MediaItem(
    media: Media,
    onClick: () -> Unit
) {
    CardItemUiComponent(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        imageUrl = media.imageUrl,
        rating = media.rating,
        onClick = onClick
    )
}
