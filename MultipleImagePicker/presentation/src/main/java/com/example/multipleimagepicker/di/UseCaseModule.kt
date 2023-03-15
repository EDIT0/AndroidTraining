package com.example.multipleimagepicker.di

import com.example.domain.repository.ImagePickerRepository
import com.example.domain.repository.ImageRepository
import com.example.domain.usecase.GetAlbumImageListUseCase
import com.example.domain.usecase.SaveSelectedImagesToServerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetAlbumImageListUseCase(imagePickerRepository: ImagePickerRepository): GetAlbumImageListUseCase {
        return GetAlbumImageListUseCase(imagePickerRepository)
    }

    @Provides
    @Singleton
    fun provideSaveSelectedImagesToServerUseCase(imageRepository: ImageRepository): SaveSelectedImagesToServerUseCase {
        return SaveSelectedImagesToServerUseCase(imageRepository)
    }
}