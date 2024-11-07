package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

data class TvShowDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("recommendations")
    val recommendations: TvShowRecommendationsDto
)

data class TvShowRecommendationsDto(
    @SerializedName("results") val tvShows: List<MediaDto.TVShow>
)
