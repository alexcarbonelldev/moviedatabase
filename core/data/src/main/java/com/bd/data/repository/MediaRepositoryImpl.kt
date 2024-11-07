package com.bd.data.repository

import com.bd.common.Either
import com.bd.data.datasource.MovieDataSource
import com.bd.domain.model.Media
import com.bd.domain.model.MovieDetail
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MediaRepository {

    override suspend fun getTrendingMedia(): Either<List<Media>> =
        movieDataSource.getTrendingMedia()

    override suspend fun getMovieDetail(id: String): Either<MovieDetail> =
        movieDataSource.getMovieDetail(id)
}
