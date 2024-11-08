package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.Content
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class SearchContent @Inject constructor(
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(query: String): Either<List<Content>> =
        mediaRepository.searchContent(query)
}
