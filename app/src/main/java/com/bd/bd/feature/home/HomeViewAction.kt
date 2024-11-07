package com.bd.bd.feature.home

import com.bd.domain.model.MediaType
import com.bd.ui.mvi.ViewAction

sealed interface HomeViewAction : ViewAction {

    data class OnMediaClicked(val mediaId: String, val mediaType: MediaType) : HomeViewAction
}
