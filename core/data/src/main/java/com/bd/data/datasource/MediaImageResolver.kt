package com.bd.data.datasource

import javax.inject.Inject

class MediaImageResolver @Inject constructor() {

    fun getImageUrl(path: String, size: ImageSize): String = IMAGE_BASE_URL + size.value + path

    enum class ImageSize(val value: String) {
        S("w185"),
        M("w342"),
        L("w500"),
        XL("w780"),
        XXL("w1280"),
    }

    private companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    }
}
