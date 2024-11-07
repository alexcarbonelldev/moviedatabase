package com.bd.bd.di

import com.bd.data.repository.MediaRepositoryImpl
import com.bd.domain.repository.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindBookRepository(movieRepositoryImpl: MediaRepositoryImpl): MediaRepository
}
