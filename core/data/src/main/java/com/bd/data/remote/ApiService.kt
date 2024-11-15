package com.bd.data.remote

import com.bd.data.remote.model.MediaResponseDto
import com.bd.data.remote.model.MovieDetailDto
import com.bd.data.remote.model.TvShowDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/3/trending/all/week")
    suspend fun getTrendingMedia(): MediaResponseDto

    @GET("/3/movie/{id}?append_to_response=recommendations")
    suspend fun getMovieDetail(@Path("id") id: String): MovieDetailDto

    @GET("/3/tv/{id}?append_to_response=recommendations")
    suspend fun getTVShowDetail(@Path("id") id: String): TvShowDetailDto

    @GET("/3/search/multi")
    suspend fun searchContent(@Query("query") query: String): MediaResponseDto
}
