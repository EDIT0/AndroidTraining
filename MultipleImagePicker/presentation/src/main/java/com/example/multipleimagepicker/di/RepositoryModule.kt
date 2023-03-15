package com.example.multipleimagepicker.di

import com.example.data.repository.ImagePickerRepositoryImpl
import com.example.data.repository.ImageRepositoryImpl
import com.example.data.repository.localDataSource.ImagePickerLocalDataSource
import com.example.data.repository.remoteDataSource.ImageRemoteDataSource
import com.example.domain.repository.ImagePickerRepository
import com.example.domain.repository.ImageRepository
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
    fun provideImagePickerRepository(
        imagePickerLocalDataSource: ImagePickerLocalDataSource
    ) : ImagePickerRepository {
        return ImagePickerRepositoryImpl(imagePickerLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        imageRemoteDataSource: ImageRemoteDataSource
    ) : ImageRepository {
        return ImageRepositoryImpl(imageRemoteDataSource)
    }
}