package com.bd.bd.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bd.domain.model.Book
import com.bd.ui.design_system.component.CardItemUi

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    HomeScreen(viewState)
}

@Composable
private fun HomeScreen(
    viewState: HomeViewState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (viewState) {
                HomeViewState.Error -> Text("Error")
                HomeViewState.Loading -> Loading()
                is HomeViewState.Success -> Success(viewState)
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
private fun Success(viewState: HomeViewState.Success) {
    Column {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Top Books",
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
        )
        LazyRow(Modifier.padding(horizontal = 4.dp)) {
            items(viewState.topBooks.size) { i ->
                BookItem(viewState.topBooks[i])
            }
        }
    }
}

@Composable
private fun BookItem(book: Book) {
    val authors = book.authors.joinToString(", ")
    CardItemUi(
        book.imageUrl,
        book.title,
        authors
    )
}
