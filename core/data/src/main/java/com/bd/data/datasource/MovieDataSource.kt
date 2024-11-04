package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.common.safeApiCall
import com.bd.data.remote.common.toEither
import com.bd.data.remote.model.MovieApiResponse
import com.bd.domain.model.Movie
import com.bd.domain.model.MovieDetail
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies(): Either<List<Movie>> = safeApiCall { apiService.getPopularMovies() }
        .toEither { apiResponse -> apiResponse.popularMovies.map { it.mapToMovie() } }

    suspend fun getMovie(id: String): Either<MovieDetail> =
        safeApiCall { apiService.getBook(id) }
            .toEither { it.mapToMovieDetail() }

    private fun MovieApiResponse.mapToMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id.toString(),
            title = title,
            imageUrl = IMAGE_BASE_URL_SMALL + poster,
        )
    }

    private fun MovieApiResponse.mapToMovie(): Movie =
        Movie(
            id = id.toString(),
            title = title,
            imageUrl = IMAGE_BASE_URL_SMALL + poster,
            rating = rating
        )

    private companion object {
        const val IMAGE_BASE_URL_SMALL = "https://image.tmdb.org/t/p/w185/"
    }
}
