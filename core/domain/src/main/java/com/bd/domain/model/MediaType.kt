package com.bd.domain.model

sealed interface ContentType {
    sealed interface Media : ContentType {
        data object Movie : Media
        data object TvShow : Media
    }

    data object Person : ContentType
}
