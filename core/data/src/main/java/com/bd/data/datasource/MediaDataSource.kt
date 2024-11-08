package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.common.safeApiCall
import com.bd.data.remote.common.toEither
import com.bd.data.remote.model.MediaDto
import com.bd.data.remote.model.MovieDetailDto
import com.bd.data.remote.model.PersonDto
import com.bd.data.remote.model.TvShowDetailDto
import com.bd.domain.model.Content
import com.bd.domain.model.ContentType
import com.bd.domain.model.Media
import com.bd.domain.model.MediaDetail
import com.bd.domain.model.Person
import javax.inject.Inject

class MediaDataSource @Inject constructor(
    private val apiService: ApiService,
    private val mediaImageResolver: MediaImageResolver
) {

    suspend fun getTrendingMedia(): Either<List<Media>> = safeApiCall { apiService.getTrendingMedia() }
        .toEither { apiResponse ->
            apiResponse.results
                .filterIsInstance<MediaDto>()
                .map { mapToMedia(it) }
        }

    suspend fun getMediaDetail(id: String, mediaType: ContentType.Media): Either<MediaDetail> = safeApiCall {
        when (mediaType) {
            ContentType.Media.Movie -> apiService.getMovieDetail(id).mapToDetail()
            ContentType.Media.TvShow -> apiService.getTVShowDetail(id).mapToDetail()
        }
    }.toEither { it }

    suspend fun searchContent(query: String): Either<List<Content>> = safeApiCall {
        apiService.searchContent(query)
    }.toEither { apiResponse ->
        apiResponse.results.map {
            when (it) {
                is MediaDto -> mapToMedia(it)
                is PersonDto -> mapToPerson(it)
            }
        }
    }

    private fun MovieDetailDto.mapToDetail(): MediaDetail {
        return MediaDetail.Movie(
            id = id.toString(),
            title = title,
            description = overview,
            imageUrl = posterPath?.let { mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.M) },
            backgroundImageUrl = backdropPath?.let {
                mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.XL)
            },
            rating = rating,
            genres = genres.map { it.name },
            recommendations = recommendations.movies
                .map { mapToMovie(it) }
                .filter { it.imageUrl != null }
        )
    }

    private fun TvShowDetailDto.mapToDetail(): MediaDetail {
        return MediaDetail.TVShow(
            id = id.toString(),
            title = name,
            description = overview,
            imageUrl = posterPath?.let { mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.M) },
            backgroundImageUrl = backdropPath?.let {
                mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.XL)
            },
            rating = rating,
            genres = genres.map { it.name },
            recommendations = recommendations.tvShows
                .map { mapToTVShow(it) }
                .filter { it.imageUrl != null }
        )
    }

    private fun mapToMedia(content: MediaDto) = when (content) {
        is MediaDto.Movie -> mapToMovie(content)
        is MediaDto.TVShow -> mapToTVShow(content)
    }

    private fun mapToMovie(movie: MediaDto.Movie): Media.Movie =
        Media.Movie(
            id = movie.id.toString(),
            title = movie.title,
            imageUrl = movie.posterPath?.let { mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.M) },
            rating = movie.voteAverage
        )

    private fun mapToTVShow(tvShow: MediaDto.TVShow): Media.TvShow =
        Media.TvShow(
            id = tvShow.id.toString(),
            title = tvShow.name,
            imageUrl = tvShow.posterPath?.let { mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.M) },
            rating = tvShow.voteAverage
        )

    private fun mapToPerson(person: PersonDto): Person =
        Person(
            id = person.id.toString(),
            title = person.name,
            imageUrl = person.profilePath?.let { mediaImageResolver.getImageUrl(it, MediaImageResolver.ImageSize.M) },
        )
}
