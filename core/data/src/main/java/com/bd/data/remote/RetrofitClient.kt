package com.bd.data.remote

import android.content.Context
import com.bd.data.BuildConfig
import com.bd.data.remote.interceptor.CacheInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    @ApplicationContext context: Context
) {

    private val cacheSize = 10 * 1024 * 1024 // 10 MB
    private val cacheDirectory = File(context.cacheDir, "http_cache")
    private val cache = Cache(cacheDirectory, cacheSize.toLong())

    private val okHttpClient = OkHttpClient.Builder().apply {
        cache(cache)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            addInterceptor(loggingInterceptor)
        }
        addInterceptor(CacheInterceptor())
    }.build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
    }
}

