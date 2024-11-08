package com.bd.bd.feature.search

import com.bd.ui.mvi.ViewState


data class SearchViewState(
    val query: String = "",
    val resultsState: ResultsState = ResultsState.Initial,
) : ViewState

sealed interface ResultsState {
    data object Initial : ResultsState
    data object Loading : ResultsState
    data class Content(val results: List<SearchResultUiModel>) : ResultsState
    data object NotFound : ResultsState
    data object Error : ResultsState
}

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
