package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.WorkApiResponse
import com.bd.data.remote.safeApiCall
import com.bd.data.remote.toEither
import com.bd.domain.model.Book
import javax.inject.Inject

class BookDataSource @Inject constructor(
    private val bookService: ApiService
) {

    suspend fun getBestSellerBooks(): Either<List<Book>> = safeApiCall { bookService.getBestSellerBooks() }
        .toEither(
            transformSuccess = { apiResponse -> apiResponse.works.map { it.mapToBook() } }
        )

    private fun WorkApiResponse.mapToBook(): Book =
        Book(
            title = title,
            imageUrl = coverId?.let { mapCoverImageUrl(it) },
            authors = authors.map { it.name }
        )

    private fun mapCoverImageUrl(coverId: String): String = "https://covers.openlibrary.org/b/id/$coverId-L.jpg"
}
