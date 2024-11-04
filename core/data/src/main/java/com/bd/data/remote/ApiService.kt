package com.bd.data.remote

import com.bd.data.remote.model.MovieApiResponse
import com.bd.data.remote.model.PopularMoviesApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): PopularMoviesApiResponse

    @GET("/3/movie/{id}")
    suspend fun getBook(@Path("id") id: String): MovieApiResponse
}
