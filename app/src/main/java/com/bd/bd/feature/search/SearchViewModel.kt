package com.bd.bd.feature.search

import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.model.Media
import com.bd.domain.model.Person
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
            is SearchViewAction.OnItemClick -> onItemClick(viewAction)
        }
    }

    private fun onQueryChange(query: String) {
        updateState(viewState.value.copy(query = query))

        if (query.length < 3) return
        launch {
            searchContent(query)
                .onSuccess { results ->
                    updateState(
                        viewState.value.copy(
                            results = results.map { item ->
                                val type = when (item) {
                                    is Media.Movie -> ResultType.MOVIE
                                    is Media.TvShow -> ResultType.TV_SHOW
                                    is Person -> ResultType.PERSON
                                }
                                SearchResultUiModel(item.id, item.title, item.imageUrl, type)
                            })
                    )
                }
                .onFailure { println("Search - Failure: ${it.message}") }
        }
    }

    private fun onItemClick(viewAction: SearchViewAction.OnItemClick) {
        when (viewAction.resultType) {
            ResultType.MOVIE -> addEvent(SearchViewEvent.NavToMovieDetail(viewAction.id))
            ResultType.TV_SHOW -> addEvent(SearchViewEvent.NavToTvShowDetail(viewAction.id))
            ResultType.PERSON -> Unit
        }
    }
}
