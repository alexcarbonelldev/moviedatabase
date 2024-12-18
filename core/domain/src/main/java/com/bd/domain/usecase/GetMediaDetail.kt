package com.bd.domain.usecase

import com.bd.common.Either
import com.bd.domain.model.ContentType
import com.bd.domain.model.MediaDetail
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class GetMediaDetail @Inject constructor(
    private val mediaRepository: MediaRepository
) {

    suspend operator fun invoke(id: String, mediaType: ContentType.Media): Either<MediaDetail> =
        mediaRepository.getMediaDetail(id, mediaType)
}
