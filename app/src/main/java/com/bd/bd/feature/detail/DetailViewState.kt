package com.bd.bd.feature.detail

import com.bd.ui.mvi.ViewState

sealed class DetailViewState : ViewState {

    data object Loading : DetailViewState()
}
