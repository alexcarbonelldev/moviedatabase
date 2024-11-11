package com.bd.bd.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.ui.R
import com.bd.ui.design_system.Icons
import com.bd.ui.design_system.component.AsyncImageUiComponent
import com.bd.ui.design_system.component.ErrorStateUiComponent
import com.bd.ui.design_system.component.LoadingStateUiComponent
import com.bd.ui.mvi.ViewEventObserver

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navToMovieDetail: (String) -> Unit,
    navToTvShowDetail: (String) -> Unit,
) {
    viewModel.ViewEventObserver { event ->
        when (event) {
            is SearchViewEvent.NavToMovieDetail -> navToMovieDetail(event.id)
            is SearchViewEvent.NavToTvShowDetail -> navToTvShowDetail(event.id)
        }
    }

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        SearchComponent(
            viewState = viewState,
            onQueryChange = { viewModel.onViewAction(SearchViewAction.OnQueryChange(it)) },
        )
        Results(
            viewState = viewState,
            onViewAction = { viewModel.onViewAction(it) }
        )
    }
}

@Composable
private fun SearchComponent(
    viewState: SearchViewState,
    onQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = viewState.query,
        onValueChange = { onQueryChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(text = stringResource(R.string.search)) },
        singleLine = true,
        leadingIcon = {
            Icon(painter = painterResource(Icons.Search), contentDescription = "Search icon")
        },
        trailingIcon = {
            AnimatedVisibility(viewState.query.isNotEmpty()) {
                Icon(
                    modifier = Modifier.clickable { onQueryChange("") },
                    painter = painterResource(Icons.Close), contentDescription = "Clear query icon",
                )
            }
        },
    )
}

@Composable
private fun Results(
    viewState: SearchViewState,
    onViewAction: (SearchViewAction) -> Unit
) {

    Crossfade(
        targetState = viewState.resultsState,
        label = "searchStateAnimation"
    ) { resultsState ->
        when (resultsState) {
            is ResultsState.Content -> ResultsContent(resultsState, onViewAction)
            ResultsState.Error -> ErrorStateUiComponent(
                onRetryClick = { onViewAction.invoke(SearchViewAction.OnRetryClick) }
            )

            ResultsState.Initial -> Initial()
            ResultsState.Loading -> LoadingStateUiComponent()
            ResultsState.NotFound -> NoResultsFound()
        }
    }
}

@Composable
private fun ResultsContent(
    resultsState: ResultsState.Content,
    onViewAction: (SearchViewAction) -> Unit,
) {
    Box {
        LazyColumn {
            items(
                count = resultsState.results.size,
                key = { i -> resultsState.results[i].id }
            ) { i ->
                ResultItem(
                    item = resultsState.results[i],
                    onItemClick = { id, type -> onViewAction(SearchViewAction.OnItemClick(id, type)) }
                )
            }
        }
    }
}

@Composable
private fun LazyItemScope.ResultItem(
    item: SearchResultUiModel,
    onItemClick: (id: String, type: ResultType) -> Unit,
) {
    Row(
        Modifier
            .animateItem()
            .clickable { onItemClick(item.id, item.type) }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val imageModifier = when (item.type) {
            ResultType.MOVIE,
            ResultType.TV_SHOW -> Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(height = 100.dp, width = 80.dp)

            ResultType.PERSON -> Modifier
                .clip(CircleShape)
                .size(height = 80.dp, width = 80.dp)
        }

        AsyncImageUiComponent(
            modifier = imageModifier,
            imageUrl = item.imageUrl,
        )

        when (item.type) {
            ResultType.MOVIE,
            ResultType.TV_SHOW -> {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(vertical = 4.dp),
                    text = item.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            ResultType.PERSON -> {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = item.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun NoResultsFound() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center),
            text = stringResource(R.string.no_results),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Initial() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.Center),
            text = stringResource(R.string.search_placeholder),
            textAlign = TextAlign.Center
        )
    }
}
