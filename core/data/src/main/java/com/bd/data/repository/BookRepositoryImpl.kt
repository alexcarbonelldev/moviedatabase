package com.bd.data.repository

import com.bd.common.Either
import com.bd.data.datasource.BookDataSource
import com.bd.domain.model.Book
import com.bd.domain.model.BookDetail
import com.bd.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {

    override suspend fun getPopularBooks(): Either<List<Book>> =
        bookDataSource.getBestSellerBooks()

    override suspend fun getBookDetail(id: String): Either<BookDetail> =
        bookDataSource.getBook(id)
}
