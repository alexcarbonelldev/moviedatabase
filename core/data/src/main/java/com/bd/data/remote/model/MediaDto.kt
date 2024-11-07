package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

sealed class MediaDto {

    data class Movie(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("poster_path") val posterPath: String
    ) : MediaDto()

    data class TVShow(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("poster_path") val posterPath: String
    ) : MediaDto()
}
