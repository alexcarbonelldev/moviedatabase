package com.bd.domain.model

data class MovieDetail(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val backgroundImageUrl: String?,
    val rating: Float,
    val genres: List<String>,
    val recommendations: List<Movie>
)