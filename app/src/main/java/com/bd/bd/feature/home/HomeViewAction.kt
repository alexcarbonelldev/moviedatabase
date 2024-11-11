package com.bd.bd.feature.home

import com.bd.domain.model.ContentType
import com.bd.ui.mvi.ViewAction

sealed interface HomeViewAction : ViewAction {

    data class OnMediaClicked(val mediaId: String, val mediaType: ContentType.Media) : HomeViewAction

    data object OnRetryClick : HomeViewAction
}
