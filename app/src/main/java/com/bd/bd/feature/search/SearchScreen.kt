package com.bd.bd.feature.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.bd.R
import com.bd.ui.design_system.Icons
import com.bd.ui.design_system.component.AsyncImageUiComponent

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        SearchComponent(
            viewState = viewState,
            onQueryChange = { viewModel.onViewAction(SearchViewAction.OnQueryChange(it)) }
        )
        Results(viewState)
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
private fun Results(viewState: SearchViewState) {
    LazyColumn {
        items(
            count = viewState.results.size,
            key = { i -> viewState.results[i].id }
        ) { i ->
            ResultItem(viewState.results[i])
        }
    }
}

@Composable
private fun ResultItem(searchResult: SearchResultUiModel) {
    Row(Modifier.padding(16.dp)) {
        AsyncImageUiComponent(
            modifier = Modifier
                .size(height = 100.dp, width = 80.dp),
            imageUrl = searchResult.imageUrl,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = searchResult.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
