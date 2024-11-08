package com.bd.bd.feature.detail

import androidx.compose.runtime.Immutable
import com.bd.domain.model.ContentType
import com.bd.ui.mvi.ViewState

sealed class DetailViewState : ViewState {

    data object Loading : DetailViewState()
    data object Error : DetailViewState()

    @Immutable
    data class Content(
        val title: String,
        val description: String,
        val imageUrl: String,
        val backgroundImageUrl: String,
        val rating: Float,
        val genres: List<String>,
        val recommendations: List<RecommendedMediaUiModel>
    ) : DetailViewState()
}

@Immutable
data class RecommendedMediaUiModel(
    val id: String,
    val imageUrl: String?,
    val rating: Float,
    val mediaType: ContentType.Media
)
