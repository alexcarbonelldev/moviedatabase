package com.bd.bd.feature.search

import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.model.Media
import com.bd.domain.model.Person
import com.bd.domain.usecase.SearchContent
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchContent: SearchContent
) : BaseViewModel<SearchViewState, SearchViewEvent, SearchViewAction>(SearchViewState()) {

    private val queryStateFlow = MutableStateFlow("")

    init {
        launch {
            queryStateFlow
                .debounce(DEBOUNCE_MILLIS)
                .collect { query ->
                    if (query.length > 1) {
                        executeQuery(query)
                    } else {
                        updateState(viewState.value.copy(resultsState = ResultsState.Initial))
                    }
                }
        }
    }

    override fun onViewAction(viewAction: SearchViewAction) {
        when (viewAction) {
            is SearchViewAction.OnQueryChange -> onQueryChange(viewAction.query)
            is SearchViewAction.OnItemClick -> onItemClick(viewAction)
        }
    }

    private fun onQueryChange(query: String) {
        updateState(viewState.value.copy(query = query))
        queryStateFlow.value = query
    }

    private fun onItemClick(viewAction: SearchViewAction.OnItemClick) {
        when (viewAction.resultType) {
            ResultType.MOVIE -> addEvent(SearchViewEvent.NavToMovieDetail(viewAction.id))
            ResultType.TV_SHOW -> addEvent(SearchViewEvent.NavToTvShowDetail(viewAction.id))
            ResultType.PERSON -> Unit
        }
    }

    private suspend fun executeQuery(query: String) {
        updateState(viewState.value.copy(resultsState = ResultsState.Loading))

        searchContent(query)
            .onSuccess { results ->
                if (results.isEmpty()) {
                    updateState(viewState.value.copy(resultsState = ResultsState.NotFound))
                } else {
                    updateState(
                        viewState.value.copy(
                            resultsState = results
                                .map { item ->
                                    val type = when (item) {
                                        is Media.Movie -> ResultType.MOVIE
                                        is Media.TvShow -> ResultType.TV_SHOW
                                        is Person -> ResultType.PERSON
                                    }
                                    SearchResultUiModel(item.id, item.title, item.imageUrl, type)
                                }.let { ResultsState.Content(it) }
                        )
                    )
                }
            }
            .onFailure {
                updateState(viewState.value.copy(resultsState = ResultsState.Error))
            }
    }

    private companion object {
        const val DEBOUNCE_MILLIS = 500L
    }
}
