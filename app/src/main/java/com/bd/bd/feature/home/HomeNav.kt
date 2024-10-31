package com.bd.bd.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

fun NavController.navigateToHome(
    navOptions: NavOptions? = null,
) {
    navigate(route = HomeDestination, navOptions)
}
