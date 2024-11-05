package com.bd.bd.feature.detail

import com.bd.ui.mvi.ViewEvent

interface DetailViewEvent : ViewEvent {
    data class NavToMovie(val id: String) : DetailViewEvent
}
