package com.example.imagesenderdemo1.presentation.di

import com.example.imagesenderdemo1.data.repository.ImageRepositoryImpl
import com.example.imagesenderdemo1.data.repository.remote.ImageRemoteDataSource
import com.example.imagesenderdemo1.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideImageRepository(
        imageRemoteDataSource: ImageRemoteDataSource
    ) : ImageRepository {
        return ImageRepositoryImpl(imageRemoteDataSource)
    }
}