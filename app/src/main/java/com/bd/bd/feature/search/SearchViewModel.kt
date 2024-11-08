package com.bd.bd.feature.search

import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.usecase.SearchContent
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchContent: SearchContent
) : BaseViewModel<SearchViewState, SearchViewEvent, SearchViewAction>(SearchViewState()) {

    override fun onViewAction(viewAction: SearchViewAction) {
        when (viewAction) {
            is SearchViewAction.OnQueryChange -> onQueryChange(viewAction.query)
            is SearchViewAction.OnItemClick -> {
                viewState.value.results
                    .firstOrNull { it.id == viewAction.id }
            }
        }
    }

    private fun onQueryChange(query: String) {
        updateState(viewState.value.copy(query = query))

        if (query.length < 3) return
        launch {
            searchContent(query)
                .onSuccess { result ->
                    println("Search - Success")
                    result.forEach { println("$it") }

                    updateState(
                        viewState.value.copy(
                            results = result.map { SearchResultUiModel(it.id, it.title, it.imageUrl) })
                    )
                }
                .onFailure { println("Search - Failure: ${it.message}") }
        }
    }
}
