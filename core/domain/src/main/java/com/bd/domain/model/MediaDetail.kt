package com.bd.domain.model

sealed interface MediaDetail {

    val id: String
    val title: String
    val description: String
    val imageUrl: String?
    val backgroundImageUrl: String?
    val rating: Float
    val genres: List<String>
    val recommendations: List<Media>

    data class Movie(
        override val id: String,
        override val title: String,
        override val description: String,
        override val imageUrl: String?,
        override val backgroundImageUrl: String?,
        override val rating: Float,
        override val genres: List<String>,
        override val recommendations: List<Media.Movie>
    ) : MediaDetail

    data class TVShow(
        override val id: String,
        override val title: String,
        override val description: String,
        override val imageUrl: String?,
        override val backgroundImageUrl: String?,
        override val rating: Float,
        override val genres: List<String>,
        override val recommendations: List<Media.TvShow>
    ) : MediaDetail
}
