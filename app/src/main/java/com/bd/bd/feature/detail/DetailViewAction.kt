package com.bd.bd.feature.detail

import com.bd.domain.model.ContentType
import com.bd.ui.mvi.ViewAction

sealed interface DetailViewAction : ViewAction {
    data class OnRecommendedMediaClick(
        val id: String,
        val mediaType: ContentType.Media
    ) : DetailViewAction

    data object OnRetryClick : DetailViewAction
}
