package com.bd.bd.feature.home

import androidx.lifecycle.viewModelScope
import com.bd.common.onFailure
import com.bd.common.onSuccess
import com.bd.domain.usecase.GetBestSellerBooks
import com.bd.ui.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBestSellerBooks: GetBestSellerBooks
) : BaseViewModel<HomeViewState, HomeViewEvent, HomeViewAction>(
    HomeViewState.Loading
) {

    init {
        initBestSellerBooks()
    }

    override fun onViewAction(viewAction: HomeViewAction) {
        TODO("Not yet implemented")
    }

    private fun initBestSellerBooks() {
        viewModelScope.launch {
            getBestSellerBooks()
                .onSuccess { updateState(HomeViewState.Success(topBooks = it)) }
                .onFailure { updateState(HomeViewState.Error) }
        }
    }
}
