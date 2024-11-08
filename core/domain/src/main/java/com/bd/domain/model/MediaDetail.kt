package com.bd.domain.model

sealed class MediaDetail(
    open val id: String,
    open val title: String,
    open val description: String,
    open val imageUrl: String?,
    open val backgroundImageUrl: String?,
    open val rating: Float,
    open val genres: List<String>,
    open val recommendations: List<Media>
) {

    data class Movie(
        override val id: String,
        override val title: String,
        override val description: String,
        override val imageUrl: String?,
        override val backgroundImageUrl: String?,
        override val rating: Float,
        override val genres: List<String>,
        override val recommendations: List<Media.Movie>
    ) : MediaDetail(id, title, description, imageUrl, backgroundImageUrl, rating, genres, recommendations)

    data class TVShow(
        override val id: String,
        override val title: String,
        override val description: String,
        override val imageUrl: String?,
        override val backgroundImageUrl: String?,
        override val rating: Float,
        override val genres: List<String>,
        override val recommendations: List<Media.TVShow>
    ) : MediaDetail(id, title, description, imageUrl, backgroundImageUrl, rating, genres, recommendations)
}

fun MediaDetail.getType(): ContentType.Media = when (this) {
    is MediaDetail.Movie -> ContentType.Media.Movie
    is MediaDetail.TVShow -> ContentType.Media.TvShow
}
