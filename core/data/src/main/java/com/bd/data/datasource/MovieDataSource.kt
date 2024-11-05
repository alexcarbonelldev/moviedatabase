package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.common.safeApiCall
import com.bd.data.remote.common.toEither
import com.bd.data.remote.model.MovieApiResponse
import com.bd.data.remote.model.MovieDetailApiResponse
import com.bd.domain.model.Movie
import com.bd.domain.model.MovieDetail
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: ApiService,
    private val movieImageResolver: MovieImageResolver
) {

    suspend fun getPopularMovies(): Either<List<Movie>> = safeApiCall { apiService.getPopularMovies() }
        .toEither { apiResponse -> apiResponse.popularMovies.map { it.mapToMovie() } }

    suspend fun getMovie(id: String): Either<MovieDetail> =
        safeApiCall { apiService.getMovie(id) }
            .toEither { it.mapToMovieDetail() }

    private fun MovieDetailApiResponse.mapToMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id.toString(),
            title = title,
            description = overview,
            imageUrl = movieImageResolver.getImageUrl(posterPath, MovieImageResolver.ImageSize.M),
            backgroundImageUrl = backdropPath?.let {
                movieImageResolver.getImageUrl(it, MovieImageResolver.ImageSize.XL)
            },
            rating = rating,
            genres = genres.map { it.name },
            recommendations = recommendations.movies.map { it.mapToMovie() }
        )
    }

    private fun MovieApiResponse.mapToMovie(): Movie =
        Movie(
            id = id.toString(),
            title = title,
            imageUrl = movieImageResolver.getImageUrl(posterPath, MovieImageResolver.ImageSize.M),
            rating = rating
        )
}
