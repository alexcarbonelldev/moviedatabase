package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.MovieDetail
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class GetMovieDetail @Inject constructor(
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(id: String): Either<MovieDetail> =
        mediaRepository.getMovieDetail(id)
}
