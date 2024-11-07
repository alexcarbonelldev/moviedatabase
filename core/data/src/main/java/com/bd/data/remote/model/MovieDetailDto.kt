package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val release: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("genres")
    val genres: List<MovieGenreDto>,
    @SerializedName("recommendations")
    val recommendations: MovieRecommendationsDto
)

data class MovieGenreDto(
    @SerializedName("name") val name: String
)

data class MovieRecommendationsDto(
    @SerializedName("results") val movies: List<MediaDto.Movie>
)
