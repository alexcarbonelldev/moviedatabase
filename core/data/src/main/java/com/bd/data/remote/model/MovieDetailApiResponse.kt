package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieDetailApiResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("release_date")
    val release: String,
    @SerializedName("vote_average")
    val rating: Float
)
