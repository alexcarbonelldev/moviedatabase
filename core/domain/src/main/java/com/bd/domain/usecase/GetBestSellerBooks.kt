package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.Book
import com.bd.domain.repository.BookRepository
import javax.inject.Inject

class GetBestSellerBooks @Inject constructor(
    private val bookRepository: BookRepository
) {

    suspend operator fun invoke(): Either<List<Book>> =
        bookRepository.getBestSellerBooks()
}
