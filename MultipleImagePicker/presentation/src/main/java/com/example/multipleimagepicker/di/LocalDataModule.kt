package com.example.multipleimagepicker.di

import android.content.Context
import com.example.data.repository.localDataSource.ImagePickerLocalDataSource
import com.example.data.repository.localDataSourceImpl.ImagePickerLocalDataSourceImpl
import com.example.data.util.ImagePicker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideImagePickerLocalDataSource(imagePicker: ImagePicker) : ImagePickerLocalDataSource {
        return ImagePickerLocalDataSourceImpl(imagePicker)
    }

    @Singleton
    @Provides
    fun provideImagePicker(context: Context) : ImagePicker {
        return ImagePicker(context)
    }
}