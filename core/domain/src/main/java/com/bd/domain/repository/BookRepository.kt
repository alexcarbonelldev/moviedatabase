package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Book

interface BookRepository {

    suspend fun getBestSellerBooks(): Either<List<Book>>
}
