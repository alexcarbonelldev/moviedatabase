package com.bd.domain.model

sealed class Media(
    open val id: String,
    open val title: String,
    open val imageUrl: String?,
    open val rating: Float
) {

    data class Movie(
        override val id: String,
        override val title: String,
        override val imageUrl: String?,
        override val rating: Float
    ) : Media(id, title, imageUrl, rating)

    data class TVShow(
        override val id: String,
        override val title: String,
        override val imageUrl: String?,
        override val rating: Float
    ) : Media(id, title, imageUrl, rating)
}

fun Media.getType(): MediaType = when (this) {
    is Media.Movie -> MediaType.MOVIE
    is Media.TVShow -> MediaType.TV_SHOW
}
