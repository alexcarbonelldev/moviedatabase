package com.bd.bd.feature.home

import androidx.lifecycle.viewModelScope
import com.bd.common.onFailure
import com.bd.common.onSuccess
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
        initBestSellerBooks()
    }

    override fun onViewAction(viewAction: HomeViewAction) {
        when (viewAction) {
            is HomeViewAction.OnMediaClicked -> addEvent(
                HomeViewEvent.NavToDetail(
                    viewAction.mediaId,
                    viewAction.mediaType
                )
            )
        }
    }

    private fun initBestSellerBooks() {
        viewModelScope.launch {
            getTrendingMedia()
                .onSuccess {
                    it.forEach { movie -> println(movie) }
                    updateState(HomeViewState.Content(topMovies = it))
                }
                .onFailure { updateState(HomeViewState.Error) }
        }
    }
}
