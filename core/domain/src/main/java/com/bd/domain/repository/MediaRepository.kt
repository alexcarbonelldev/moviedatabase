package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Media
import com.bd.domain.model.MovieDetail

interface MediaRepository {

    suspend fun getTrendingMedia(): Either<List<Media>>

    suspend fun getMovieDetail(id: String): Either<MovieDetail>
}
