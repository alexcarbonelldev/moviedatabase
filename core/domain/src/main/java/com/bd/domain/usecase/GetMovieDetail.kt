package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.MovieDetail
import com.bd.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(id: String): Either<MovieDetail> =
        movieRepository.getMovieDetail(id)
}
