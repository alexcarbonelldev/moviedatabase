package com.bd.bd.feature.search

import com.bd.ui.mvi.ViewState

data class SearchViewState(
    val query: String = "",
    val results: List<SearchResultUiModel> = emptyList()
) : ViewState

data class SearchResultUiModel(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val type: ResultType
)

enum class ResultType {
    MOVIE,
    TV_SHOW,
    PERSON
}
