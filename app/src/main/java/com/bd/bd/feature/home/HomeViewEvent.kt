package com.bd.bd.feature.home

import com.bd.domain.model.MediaType
import com.bd.ui.mvi.ViewEvent

sealed interface HomeViewEvent : ViewEvent {
    class NavToDetail(val mediaId: String, val mediaType: MediaType) : HomeViewEvent
}
