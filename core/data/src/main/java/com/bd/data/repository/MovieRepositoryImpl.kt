package com.bd.data.repository

import com.bd.common.Either
import com.bd.data.datasource.MovieDataSource
import com.bd.domain.model.Movie
import com.bd.domain.model.MovieDetail
import com.bd.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDataSource: MovieDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(): Either<List<Movie>> =
        movieDataSource.getPopularMovies()

    override suspend fun getMovieDetail(id: String): Either<MovieDetail> =
        movieDataSource.getMovie(id)
}
