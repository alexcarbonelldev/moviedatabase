package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

sealed interface ContentDto

sealed class MediaDto : ContentDto {

    data class Movie(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("poster_path") val posterPath: String?
    ) : MediaDto()

    data class TVShow(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("poster_path") val posterPath: String?
    ) : MediaDto()
}

data class PersonDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String?
) : ContentDto
