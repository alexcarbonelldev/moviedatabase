package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Book
import com.bd.domain.model.BookDetail

interface BookRepository {

    suspend fun getPopularBooks(): Either<List<Book>>

    suspend fun getBookDetail(id: String): Either<BookDetail>
}
