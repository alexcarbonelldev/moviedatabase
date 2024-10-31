package com.bd.bd.feature.home

import com.bd.ui.mvi.ViewAction

sealed interface HomeViewAction : ViewAction {

    data class OnBookClicked(val bookId: String) : HomeViewAction
}
