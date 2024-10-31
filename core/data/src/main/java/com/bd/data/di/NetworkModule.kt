package com.bd.data.di

import com.bd.data.remote.ApiService
import com.bd.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(retrofitClient: RetrofitClient): ApiService = retrofitClient.apiService
}
