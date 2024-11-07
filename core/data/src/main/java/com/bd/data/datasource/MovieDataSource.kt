package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.common.safeApiCall
import com.bd.data.remote.common.toEither
import com.bd.data.remote.model.MediaDto
import com.bd.data.remote.model.MovieDetailDto
import com.bd.domain.model.Media
import com.bd.domain.model.MovieDetail
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val apiService: ApiService,
    private val movieImageResolver: MovieImageResolver
) {

    suspend fun getTrendingMedia(): Either<List<Media>> = safeApiCall { apiService.getTrendingMedia() }
        .toEither { apiResponse ->
            apiResponse.results.map { mapToMedia(it) }
        }

    suspend fun getMovieDetail(id: String): Either<MovieDetail> =
        safeApiCall { apiService.getMovie(id) }
            .toEither { it.mapToMovieDetail() }

    private fun MovieDetailDto.mapToMovieDetail(): MovieDetail {
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
            recommendations = recommendations.movies.map { mapToMovie(it) }
        )
    }

    private fun mapToMedia(media: MediaDto) = when (media) {
        is MediaDto.Movie -> mapToMovie(media)
        is MediaDto.TVShow -> mapToTVShow(media)
    }

    private fun mapToMovie(movie: MediaDto.Movie): Media.Movie =
        Media.Movie(
            id = movie.id.toString(),
            title = movie.title,
            imageUrl = movieImageResolver.getImageUrl(movie.posterPath, MovieImageResolver.ImageSize.M),
            rating = movie.voteAverage
        )

    private fun mapToTVShow(tvShow: MediaDto.TVShow): Media.TVShow =
        Media.TVShow(
            id = tvShow.id.toString(),
            title = tvShow.name,
            imageUrl = movieImageResolver.getImageUrl(tvShow.posterPath, MovieImageResolver.ImageSize.M),
            rating = tvShow.voteAverage
        )
}
