package com.bd.ui.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<VS : ViewState, VE : ViewEvent, VA : ViewAction>(initialViewState: VS) : ViewModel() {

    private val _viewState = MutableStateFlow(initialViewState)
    val viewState: StateFlow<VS> get() = _viewState

    private val _viewEvents = MutableStateFlow<List<VE>>(emptyList())
    val viewEvents: StateFlow<List<VE>> get() = _viewEvents

    abstract fun onViewAction(viewAction: VA)

    protected fun updateState(state: VS) {
        _viewState.value = state
    }

    protected fun addEvent(event: VE) {
        _viewEvents.value = viewEvents.value + event
    }

    fun onEventConsumed(event: VE) {
        _viewEvents.value -= event
    }
}
