package com.bd.bd.feature.search

import com.bd.ui.mvi.ViewAction

sealed interface SearchViewAction : ViewAction {

    data class OnQueryChange(val query: String) : SearchViewAction

    data class OnItemClick(val id: String) : SearchViewAction
}