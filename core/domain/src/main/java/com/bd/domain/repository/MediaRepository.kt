package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Media
import com.bd.domain.model.MediaDetail
import com.bd.domain.model.MediaType

interface MediaRepository {

    suspend fun getTrendingMedia(): Either<List<Media>>

    suspend fun getMediaDetail(id: String, mediaType: MediaType): Either<MediaDetail>
}
