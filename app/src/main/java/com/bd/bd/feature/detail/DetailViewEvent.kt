package com.bd.bd.feature.detail

import com.bd.ui.mvi.ViewEvent

sealed interface DetailViewEvent : ViewEvent {
    data class NavToMovieDetail(val id: String) : DetailViewEvent
    data class NavToTvShowDetail(val id: String) : DetailViewEvent
}
