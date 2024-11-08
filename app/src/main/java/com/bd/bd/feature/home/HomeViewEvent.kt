package com.bd.bd.feature.home

import com.bd.ui.mvi.ViewEvent

sealed interface HomeViewEvent : ViewEvent {
    data class NavToMovieDetail(val id: String) : HomeViewEvent
    data class NavToTvShowDetail(val id: String) : HomeViewEvent
}
