package com.example.imagesenderdemo1.presentation.di

import com.example.imagesenderdemo1.data.api.ApiService
import com.example.imagesenderdemo1.data.repository.remote.ImageRemoteDataSource
import com.example.imagesenderdemo1.data.repository.remote.ImageRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideImageRemoteDataSource(apiService: ApiService) : ImageRemoteDataSource {
        return ImageRemoteDataSourceImpl(apiService)
    }
}