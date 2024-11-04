package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

data class PopularMoviesApiResponse(
    @SerializedName("results")
    val popularMovies: List<MovieApiResponse>
)

