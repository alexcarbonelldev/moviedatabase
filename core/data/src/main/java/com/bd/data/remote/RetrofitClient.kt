package com.bd.data.remote

import android.content.Context
import com.bd.data.BuildConfig
import com.bd.data.remote.adapter.ContentTypeAdapter
import com.bd.data.remote.interceptor.CacheInterceptor
import com.bd.data.remote.model.ContentDto
import com.google.gson.GsonBuilder
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

    private val cacheSize: Int = 10 * 1024 * 1024 // 10 MB
    private val cacheDirectory = File(context.cacheDir, "http_cache")
    private val cache = Cache(cacheDirectory, cacheSize.toLong())

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(getGsonConverterFactory())
            .build()
            .create(ApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(loggingInterceptor)
            }
            addInterceptor(CacheInterceptor())
        }.build()
    }

    private fun getGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(ContentDto::class.java, ContentTypeAdapter())
            .create()
        return GsonConverterFactory.create(gson)
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
    }
}
