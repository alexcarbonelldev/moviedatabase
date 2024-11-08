package com.bd.bd.feature.search

import com.bd.ui.mvi.ViewEvent

sealed interface SearchViewEvent : ViewEvent {
    data class NavToMovieDetail(val id: String) : SearchViewEvent
    data class NavToTvShowDetail(val id: String) : SearchViewEvent
}
