package com.bd.data.datasource

import com.bd.common.Either
import com.bd.data.remote.ApiService
import com.bd.data.remote.BookDetailApiResponse
import com.bd.data.remote.WorkApiResponse
import com.bd.data.remote.safeApiCall
import com.bd.data.remote.toEither
import com.bd.domain.model.Book
import com.bd.domain.model.BookDetail
import javax.inject.Inject

class BookDataSource @Inject constructor(
    private val bookService: ApiService
) {

    suspend fun getBestSellerBooks(): Either<List<Book>> = safeApiCall { bookService.getPopularBooks() }
        .toEither { apiResponse -> apiResponse.works.map { it.mapToBook() } }

    suspend fun getBook(id: String): Either<BookDetail> =
        safeApiCall { bookService.getBook(id) }
            .toEither { it.mapToBookDetail() }

    private fun BookDetailApiResponse.mapToBookDetail(): BookDetail {
        return BookDetail(
            id = mapKey(key),
            title = title,
            imageUrl = covers?.firstOrNull { it != "-1" }?.let { mapCoverImageUrl(it) },
            description = description
        )
    }

    private fun WorkApiResponse.mapToBook(): Book =
        Book(
            id = mapKey(key),
            title = title,
            imageUrl = coverId?.let { mapCoverImageUrl(it) },
            authors = authors,
        )

    private fun mapKey(key: String): String {
        return key.removePrefix("/works/")
    }

    private fun mapCoverImageUrl(coverId: String): String = "https://covers.openlibrary.org/b/id/$coverId-L.jpg"
}
