package com.example.imagesenderdemo1.presentation.di

import com.example.imagesenderdemo1.domain.repository.ImageRepository
import com.example.imagesenderdemo1.domain.usecase.DeleteImageUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImageToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImagesToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SelectTotalSavedImageUseCase
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
    fun provideSaveImageToServerUseCase(imageRepository: ImageRepository): SaveImageToServerUseCase {
        return SaveImageToServerUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideSaveImagesToServerUseCase(imageRepository: ImageRepository): SaveImagesToServerUseCase {
        return SaveImagesToServerUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideSelectTotalSavedImageUseCase(imageRepository: ImageRepository): SelectTotalSavedImageUseCase {
        return SelectTotalSavedImageUseCase(imageRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteImageUseCase(imageRepository: ImageRepository): DeleteImageUseCase {
        return DeleteImageUseCase(imageRepository)
    }
}