package com.bd.common.extensions

import java.util.Locale

fun Float.toStringWithDecimals(decimals: Int = 1): String =
    String.format(locale = Locale.getDefault(), "%.${decimals}f", this)
