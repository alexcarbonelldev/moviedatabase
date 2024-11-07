package com.bd.data.remote.model

import com.google.gson.annotations.SerializedName

data class MediaResponseDto(
    @SerializedName("results")
    val results: List<MediaDto>
)
