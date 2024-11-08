package com.bd.bd.feature.detail

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class DetailDestination(
    val mediaId: String,
    val type: DetailType
)

enum class DetailType {
    MOVIE,
    TV_SHOW
}

fun NavController.navigateToMovieDetail(
    mediaId: String,
    navOptions: NavOptions? = null,
) {
    navigate(route = DetailDestination(mediaId, DetailType.MOVIE), navOptions)
}

fun NavController.navigateToTvShowDetail(
    mediaId: String,
    navOptions: NavOptions? = null,
) {
    navigate(route = DetailDestination(mediaId, DetailType.TV_SHOW), navOptions)
}
