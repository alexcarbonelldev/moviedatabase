package com.bd.data.remote

import com.bd.data.remote.model.MovieDetailApiResponse
import com.bd.data.remote.model.PopularMoviesApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/3/movie/popular${ApiKeyProvider.API_KEY_PARAM}")
    suspend fun getPopularMovies(): PopularMoviesApiResponse

    @GET("/3/movie/{id}${ApiKeyProvider.API_KEY_PARAM}&append_to_response=recommendations")
    suspend fun getMovie(@Path("id") id: String): MovieDetailApiResponse
}
