package com.bd.data.repository

import com.bd.common.Either
import com.bd.data.datasource.MediaDataSource
import com.bd.domain.model.Media
import com.bd.domain.model.MediaDetail
import com.bd.domain.model.MediaType
import com.bd.domain.repository.MediaRepository
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val mediaDataSource: MediaDataSource
) : MediaRepository {

    override suspend fun getTrendingMedia(): Either<List<Media>> =
        mediaDataSource.getTrendingMedia()

    override suspend fun getMediaDetail(id: String, mediaType: MediaType): Either<MediaDetail> {
        return mediaDataSource.getMediaDetail(id, mediaType)
    }
}
