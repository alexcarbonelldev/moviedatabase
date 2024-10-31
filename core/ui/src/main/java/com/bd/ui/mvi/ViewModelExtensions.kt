package com.bd.ui.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch { block() }
}

@Composable
fun <VS : ViewState, VE : ViewEvent, VI : ViewAction> BaseViewModel<VS, VE, VI>.ViewEventObserver(
    onEvent: (VE) -> Unit
) {
    LaunchedEffect(Unit) {
        this@ViewEventObserver.viewEvents.collect { events ->
            if (events.isEmpty()) return@collect

            val event = events.first()
            onEvent(event)
            this@ViewEventObserver.onEventConsumed(event)
        }
    }
}
