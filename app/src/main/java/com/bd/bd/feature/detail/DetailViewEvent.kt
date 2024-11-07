package com.bd.bd.feature.detail

import com.bd.domain.model.MediaType
import com.bd.ui.mvi.ViewEvent

interface DetailViewEvent : ViewEvent {
    data class NavToMedia(val id: String, val mediaType: MediaType) : DetailViewEvent
}
