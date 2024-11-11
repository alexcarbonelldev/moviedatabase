package com.bd.bd.feature.home

import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.model.ContentType
import com.bd.domain.model.Media
import com.bd.domain.usecase.GetTrendingMedia
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMedia: GetTrendingMedia
) : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewAction>(
    HomeViewState.Loading
) {

    init {
        launch { init() }
    }

    override fun onViewAction(viewAction: HomeViewAction) {
        when (viewAction) {
            is HomeViewAction.OnMediaClicked -> when (viewAction.mediaType) {
                ContentType.Media.Movie -> HomeViewEvent.NavToMovieDetail(viewAction.mediaId)
                ContentType.Media.TvShow -> HomeViewEvent.NavToTvShowDetail(viewAction.mediaId)
            }.let { addEvent(it) }

            HomeViewAction.OnRetryClick -> launch { init() }
        }
    }

    private suspend fun init() {
        updateState(HomeViewState.Loading)

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

    private fun splitTrendingList(list: List<Media>): Pair<List<Media.Movie>, List<Media.TvShow>> {
        val movies = mutableListOf<Media.Movie>()
        val tvShows = mutableListOf<Media.TvShow>()

        list.forEach { item ->
            when (item) {
                is Media.Movie -> movies.add(item)
                is Media.TvShow -> tvShows.add(item)
            }
        }

        return movies to tvShows
    }
}
