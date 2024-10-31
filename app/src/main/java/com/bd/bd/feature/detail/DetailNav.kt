package com.bd.bd.feature.detail

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class DetailDestination(val bookId: String)

fun NavController.navigateToDetail(
    bookId: String,
    navOptions: NavOptions? = null,
) {
    navigate(route = DetailDestination(bookId), navOptions)
}
