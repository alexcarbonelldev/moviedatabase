package com.bd.data.remote

import com.bd.data.BuildConfig

object ApiKeyProvider {

    val API_KEY_PARAM: String get() = "?api_key=${BuildConfig.TMDB_API_KEY}"
}
