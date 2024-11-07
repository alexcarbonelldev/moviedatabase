package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.Media
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class GetTrendingMedia @Inject constructor(
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(): Either<List<Media>> =
        mediaRepository.getTrendingMedia()
}
