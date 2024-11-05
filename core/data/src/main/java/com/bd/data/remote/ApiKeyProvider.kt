package com.bd.data.remote

import com.bd.data.BuildConfig

object ApiKeyProvider {

    const val API_KEY_PARAM: String = "?api_key=${BuildConfig.TMDB_API_KEY}"
}
