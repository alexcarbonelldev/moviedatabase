package com.bd.domain.model

sealed interface Content {
    val id: String
    val title: String
    val imageUrl: String?
}

sealed class Media(
    open val rating: Float
) : Content {

    data class Movie(
        override val id: String,
        override val title: String,
        override val imageUrl: String?,
        override val rating: Float
    ) : Media(rating)

    data class TVShow(
        override val id: String,
        override val title: String,
        override val imageUrl: String?,
        override val rating: Float
    ) : Media(rating)
}

data class Person(
    override val id: String,
    override val title: String,
    override val imageUrl: String?,
) : Content

fun Media.getType(): ContentType.Media = when (this) {
    is Media.Movie -> ContentType.Media.Movie
    is Media.TVShow -> ContentType.Media.TvShow
}
