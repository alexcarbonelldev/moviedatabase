package com.bd.bd.feature.home

import com.bd.ui.mvi.ViewEvent

sealed interface HomeViewEvent : ViewEvent {
    class NavToDetail(val bookId: String) : HomeViewEvent
}
