package com.bd.bd.feature.home

import androidx.compose.runtime.Immutable
import com.bd.domain.model.Media
import com.bd.ui.mvi.ViewState

sealed class HomeViewState : ViewState {
    data object Loading : HomeViewState()
    data object Error : HomeViewState()

    @Immutable
    data class Content(
        val trendingMovies: List<Media.Movie>,
        val trendingTvShows: List<Media.TvShow>,
    ) : HomeViewState()
}
