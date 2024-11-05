package com.bd.bd.feature.detail

import com.bd.ui.mvi.ViewAction

interface DetailViewAction : ViewAction {
    data class OnRecommendedMovieClicked(val id: String) : DetailViewAction
}
