package com.bd.bd.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.usecase.GetMovieDetail
import com.bd.ui.mvi.BaseViewModel
import com.bd.ui.mvi.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetail,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailViewState, DetailViewEvent, DetailViewAction>(
    DetailViewState.Loading
) {

    private val args: DetailDestination = savedStateHandle.toRoute()

    init {
        launch {
            getMovieDetail(args.bookId)
                .onSuccess {
                    println("Book Detail: $it")
                }
                .onFailure {
                    println("Error: $it")
                }
        }
    }

    override fun onViewAction(viewAction: DetailViewAction) {
        TODO("Not yet implemented")
    }
}
