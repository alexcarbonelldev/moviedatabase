package com.bd.data.remote

import com.bd.data.remote.model.MediaResponseDto
import com.bd.data.remote.model.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/3/trending/all/week${ApiKeyProvider.API_KEY_PARAM}")
    suspend fun getTrendingMedia(): MediaResponseDto

    @GET("/3/movie/{id}${ApiKeyProvider.API_KEY_PARAM}&append_to_response=recommendations")
    suspend fun getMovie(@Path("id") id: String): MovieDetailDto
}
