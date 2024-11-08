package com.bd.domain.repository

import com.bd.common.Either
import com.bd.domain.model.Content
import com.bd.domain.model.ContentType
import com.bd.domain.model.Media
import com.bd.domain.model.MediaDetail

interface MediaRepository {

    suspend fun getTrendingMedia(): Either<List<Media>>

    suspend fun getMediaDetail(id: String, mediaType: ContentType.Media): Either<MediaDetail>

    suspend fun searchContent(query: String): Either<List<Content>>
}
