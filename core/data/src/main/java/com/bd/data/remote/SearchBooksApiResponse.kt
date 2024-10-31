package com.bd.data.remote

import com.google.gson.annotations.SerializedName

data class SearchBooksApiResponse(
    @SerializedName("docs") val works: List<WorkApiResponse>
)

data class WorkApiResponse(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("cover_i") val coverId: String? = null,
    @SerializedName("author_name") val authors: List<String>,
)
