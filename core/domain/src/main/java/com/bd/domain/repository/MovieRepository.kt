package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Movie
import com.bd.domain.model.MovieDetail

interface MovieRepository {

    suspend fun getPopularMovies(): Either<List<Movie>>

    suspend fun getMovieDetail(id: String): Either<MovieDetail>
}
