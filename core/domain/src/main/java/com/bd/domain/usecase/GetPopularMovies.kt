package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.Movie
import com.bd.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): Either<List<Movie>> =
        movieRepository.getPopularMovies()
}
