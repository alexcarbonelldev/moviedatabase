package com.bd.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("subjects/fantasy.json?limit=10")
    suspend fun getBestSellerBooks(): BestSellerBooksApiResponse
}
