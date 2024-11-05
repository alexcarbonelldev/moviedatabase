package com.bd.bd.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.usecase.GetMovieDetail
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailViewState, DetailViewEvent, DetailViewAction>(
    DetailViewState.Loading
) {

    private val args: DetailDestination = savedStateHandle.toRoute()

    init {
        launch {
            getMovieDetail(args.movieId)
                .onSuccess {
                    updateState(
                        DetailViewState.Content(
                            title = it.title,
                            description = it.description,
                            imageUrl = it.imageUrl ?: "",
                            backgroundImageUrl = it.backgroundImageUrl ?: "",
                            rating = it.rating,
                            genres = it.genres,
                            recommendations = it.recommendations.map { recommendation ->
                                RecommendedMovieUiModel(
                                    recommendation.id,
                                    recommendation.imageUrl,
                                    recommendation.rating
                                )
                            }
                        )
                    )
                }
                .onFailure {
                    println("Error: $it")
                }
        }
    }

    override fun onViewAction(viewAction: DetailViewAction) {
        when (viewAction) {
            is DetailViewAction.OnRecommendedMovieClicked -> {
                addEvent(DetailViewEvent.NavToMovie(viewAction.id))
            }
        }
    }
}
