package com.bd.bd.feature.detail

import com.bd.domain.model.MediaType
import com.bd.ui.mvi.ViewAction

interface DetailViewAction : ViewAction {
    data class OnRecommendedMediaClick(
        val id: String,
        val mediaType: MediaType
    ) : DetailViewAction
}
