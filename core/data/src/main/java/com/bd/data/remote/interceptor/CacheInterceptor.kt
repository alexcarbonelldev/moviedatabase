package com.bd.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Cache-Control", "public, max-age=604800") // 1 week cache
            .build()
        return chain.proceed(request)
    }
}
