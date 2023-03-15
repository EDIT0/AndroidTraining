package com.example.multipleimagepicker.di

import com.example.data.api.ApiService
import com.example.data.repository.remoteDataSource.ImageRemoteDataSource
import com.example.data.repository.remoteDataSourceImpl.ImageRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Singleton
    @Provides
    fun provideImageRemoteDataSource(apiService: ApiService) : ImageRemoteDataSource {
        return ImageRemoteDataSourceImpl(apiService)
    }
}