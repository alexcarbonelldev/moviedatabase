package com.bd.bd.common.nav

import android.os.Bundle
import androidx.navigation.NavType
import com.bd.domain.model.ContentType

private const val MOVIE_TYPE = "movie"
private const val TV_SHOW_TYPE = "tv"

val mediaTypeArgName = "mediaType"
val mediaTypeArgNavType = object : NavType<ContentType.Media>(isNullableAllowed = false) {

    override fun get(bundle: Bundle, key: String): ContentType.Media? =
        bundle.getString(key)?.toContentMediaType()

    override fun parseValue(value: String): ContentType.Media =
        value.toContentMediaType()
            ?: throw IllegalArgumentException("Invalid media type: $value")

    override fun put(bundle: Bundle, key: String, value: ContentType.Media) {
        val stringValue = when (value) {
            ContentType.Media.Movie -> MOVIE_TYPE
            ContentType.Media.TvShow -> TV_SHOW_TYPE
        }
        bundle.putString(key, stringValue)
    }

    private fun String.toContentMediaType(): ContentType.Media? = when (this) {
        MOVIE_TYPE -> ContentType.Media.Movie
        TV_SHOW_TYPE -> ContentType.Media.TvShow
        else -> null
    }
}
