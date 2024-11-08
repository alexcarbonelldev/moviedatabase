package com.bd.bd.feature.home

import androidx.lifecycle.viewModelScope
import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.model.ContentType
import com.bd.domain.model.Media
import com.bd.domain.usecase.GetTrendingMedia
import com.bd.ui.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMedia: GetTrendingMedia
) : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewAction>(
    HomeViewState.Loading
) {

    init {
        init()
    }

    override fun onViewAction(viewAction: HomeViewAction) {
        when (viewAction) {
            is HomeViewAction.OnMediaClicked -> when (viewAction.mediaType) {
                ContentType.Media.Movie -> HomeViewEvent.NavToMovieDetail(viewAction.mediaId)
                ContentType.Media.TvShow -> HomeViewEvent.NavToTvShowDetail(viewAction.mediaId)
            }.let { addEvent(it) }
        }
    }

    private fun init() {
        viewModelScope.launch {
            getTrendingMedia()
                .onSuccess { result ->
                    val (trendingMovies, trendingTvShows) = splitTrendingList(result)
                    updateState(
                        HomeViewState.Content(
                            trendingMovies = trendingMovies,
                            trendingTvShows = trendingTvShows
                        )
                    )
                }
                .onFailure { updateState(HomeViewState.Error) }
        }
    }

    private fun splitTrendingList(list: List<Media>): Pair<List<Media.Movie>, List<Media.TVShow>> {
        val movies = mutableListOf<Media.Movie>()
        val tvShows = mutableListOf<Media.TVShow>()

        list.forEach { item ->
            when (item) {
                is Media.Movie -> movies.add(item)
                is Media.TVShow -> tvShows.add(item)
            }
        }

        return movies to tvShows
    }
}
