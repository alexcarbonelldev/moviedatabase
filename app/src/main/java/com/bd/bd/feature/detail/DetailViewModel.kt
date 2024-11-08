package com.bd.bd.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.model.ContentType
import com.bd.domain.model.getType
import com.bd.domain.usecase.GetMediaDetail
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMediaDetail: GetMediaDetail,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailViewState, DetailViewEvent, DetailViewAction>(
    DetailViewState.Loading
) {

    private val args: DetailDestination = savedStateHandle.toRoute()

    init {
        launch {
            val mediaType = when (args.type) {
                DetailType.MOVIE -> ContentType.Media.Movie
                DetailType.TV_SHOW -> ContentType.Media.TvShow
            }
            getMediaDetail(args.mediaId, mediaType)
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
                                RecommendedMediaUiModel(
                                    id = recommendation.id,
                                    imageUrl = recommendation.imageUrl,
                                    rating = recommendation.rating,
                                    mediaType = recommendation.getType()
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
            is DetailViewAction.OnRecommendedMediaClick -> {
                when (viewAction.mediaType) {
                    ContentType.Media.Movie -> DetailViewEvent.NavToMovieDetail(viewAction.id)
                    ContentType.Media.TvShow -> DetailViewEvent.NavToTvShowDetail(viewAction.id)
                }.let { addEvent(it) }
            }
        }
    }
}
