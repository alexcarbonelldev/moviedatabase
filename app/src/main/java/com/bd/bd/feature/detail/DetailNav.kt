package com.bd.bd.feature.detail

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bd.domain.model.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class DetailDestination(
    val mediaId: String,
    val mediaType: MediaType
)

fun NavController.navigateToDetail(
    mediaId: String,
    mediaType: MediaType,
    navOptions: NavOptions? = null,
) {
    navigate(route = DetailDestination(mediaId, mediaType), navOptions)
}
