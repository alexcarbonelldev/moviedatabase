package com.bd.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

@Composable
fun Int.toDp(): Float {
    val density = LocalDensity.current.density
    return this / density
}
