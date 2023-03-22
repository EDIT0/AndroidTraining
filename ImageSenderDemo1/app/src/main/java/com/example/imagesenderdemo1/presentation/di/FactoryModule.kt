package com.example.imagesenderdemo1.presentation.di

import android.app.Application
import com.example.imagesenderdemo1.data.util.NetworkManager
import com.example.imagesenderdemo1.domain.usecase.DeleteImageUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImageToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SaveImagesToServerUseCase
import com.example.imagesenderdemo1.domain.usecase.SelectTotalSavedImageUseCase
import com.example.imagesenderdemo1.presentation.viewmodel.ImageDecoViewPagerViewModelFactory
import com.example.imagesenderdemo1.presentation.viewmodel.ImageZoomInOutViewModelFactory
import com.example.imagesenderdemo1.presentation.viewmodel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        app: Application,
        networkManager: NetworkManager,
        saveImageToServerUseCase: SaveImageToServerUseCase,
        saveImagesToServerUseCase: SaveImagesToServerUseCase,
        selectTotalSavedImageUseCase: SelectTotalSavedImageUseCase
    ) : MainViewModelFactory {
        return MainViewModelFactory(
            app,
            networkManager,
            saveImageToServerUseCase,
            saveImagesToServerUseCase,
            selectTotalSavedImageUseCase
        )
    }

    @Provides
    @Singleton
    fun provideImageZoomInOutViewModelFactory(
        app: Application,
        networkManager: NetworkManager,
        deleteImageUseCase: DeleteImageUseCase
    ) : ImageZoomInOutViewModelFactory {
        return ImageZoomInOutViewModelFactory(
            app,
            networkManager,
            deleteImageUseCase
        )
    }

    @Provides
    @Singleton
    fun provideImageDecoViewPagerViewModelFactory(
        app: Application,
        networkManager: NetworkManager
    ) : ImageDecoViewPagerViewModelFactory {
        return ImageDecoViewPagerViewModelFactory(
            app,
            networkManager
        )
    }
}