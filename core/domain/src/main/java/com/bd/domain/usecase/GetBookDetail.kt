package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.BookDetail
import com.bd.domain.repository.BookRepository
import javax.inject.Inject

class GetBookDetail @Inject constructor(
    private val bookRepository: BookRepository
) {

    suspend operator fun invoke(id: String): Either<BookDetail> =
        bookRepository.getBookDetail(id)
}
