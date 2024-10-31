package com.bd.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("search.json?subject=fiction&first_publish_year=2024&sort=already_read&limit=10&language=eng")
    suspend fun getPopularBooks(): SearchBooksApiResponse

    @GET("works/{id}.json")
    suspend fun getBook(@Path("id") id: String): BookDetailApiResponse
}
