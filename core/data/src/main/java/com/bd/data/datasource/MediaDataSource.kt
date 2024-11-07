package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.common.safeApiCall
import com.bd.data.remote.common.toEither
import com.bd.data.remote.model.MediaDto
import com.bd.data.remote.model.MovieDetailDto
import com.bd.data.remote.model.TvShowDetailDto
import com.bd.domain.model.Media
import com.bd.domain.model.MediaDetail
import com.bd.domain.model.MediaType
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    private val apiService: ApiService,
    private val mediaImageResolver: MediaImageResolver
) {

    suspend fun getTrendingMedia(): Either<List<Media>> = safeApiCall { apiService.getTrendingMedia() }
        .toEither { apiResponse ->
            apiResponse.results.map { mapToMedia(it) }
        }

    suspend fun getMediaDetail(id: String, mediaType: MediaType): Either<MediaDetail> = safeApiCall {
        when (mediaType) {
            MediaType.MOVIE -> apiService.getMovieDetail(id).mapToDetail()
            MediaType.TV_SHOW -> apiService.getTVShowDetail(id).mapToDetail()
        }
    }.toEither { it }

    private fun MovieDetailDto.mapToDetail(): MediaDetail {
        return MediaDetail.Movie(
            id = id.toString(),
            title = title,
            description = overview,
            imageUrl = mediaImageResolver.getImageUrl(posterPath, MediaImageResolver.ImageSize.M),
            backgroundImageUrl = backdropPath?.let {
                mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.XL)
            },
            rating = rating,
            genres = genres.map { it.name },
            recommendations = recommendations.movies.map { mapToMovie(it) }
        )
    }

    private fun TvShowDetailDto.mapToDetail(): MediaDetail {
        return MediaDetail.TVShow(
            id = id.toString(),
            title = name,
            description = overview,
            imageUrl = mediaImageResolver.getImageUrl(posterPath, MediaImageResolver.ImageSize.M),
            backgroundImageUrl = backdropPath?.let {
                mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.XL)
            },
            rating = rating,
            genres = genres.map { it.name },
            recommendations = recommendations.tvShows.map { mapToTVShow(it) }
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
            imageUrl = mediaImageResolver.getImageUrl(movie.posterPath, MediaImageResolver.ImageSize.M),
            rating = movie.voteAverage
        )

    private fun mapToTVShow(tvShow: MediaDto.TVShow): Media.TVShow =
        Media.TVShow(
            id = tvShow.id.toString(),
            title = tvShow.name,
            imageUrl = mediaImageResolver.getImageUrl(tvShow.posterPath, MediaImageResolver.ImageSize.M),
            rating = tvShow.voteAverage
        )
}
