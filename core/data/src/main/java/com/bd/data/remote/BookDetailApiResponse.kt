package com.bd.data.remote

import com.google.gson.annotations.SerializedName

data class BookDetailApiResponse(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("covers") val covers: List<String>? = null,
    @SerializedName("description") val description: String,
    // Pending: Add authors
)
