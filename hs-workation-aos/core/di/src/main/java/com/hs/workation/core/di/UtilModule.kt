package com.hs.workation.core.di

import com.hs.workation.core.util.CameraGalleryUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun providesCameraGalleryUtil(): CameraGalleryUtil {
        return CameraGalleryUtil()
    }

}