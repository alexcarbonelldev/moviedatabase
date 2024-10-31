package com.bd.data.remote

import com.google.gson.annotations.SerializedName

data class BestSellerBooksApiResponse(
    @SerializedName("works") val works: List<WorkApiResponse>
)

data class WorkApiResponse(
    @SerializedName("title") val title: String,
    @SerializedName("cover_id") val coverId: String? = null,
    @SerializedName("authors") val authors: List<AuthorApiResponse>,
)

data class AuthorApiResponse(
    @SerializedName("name") val name: String
)
