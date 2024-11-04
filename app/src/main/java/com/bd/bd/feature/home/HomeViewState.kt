package com.bd.bd.feature.home

import androidx.compose.runtime.Immutable
import com.bd.domain.model.Movie
import com.bd.ui.mvi.ViewState

sealed class HomeViewState : ViewState {
    data object Loading : HomeViewState()
    data object Error : HomeViewState()

    @Immutable
    data class Success(
        val topMovies: List<Movie>
    ) : HomeViewState()
}
