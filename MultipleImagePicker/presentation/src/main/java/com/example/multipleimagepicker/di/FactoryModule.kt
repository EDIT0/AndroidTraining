package com.example.multipleimagepicker.di

import android.app.Application
import com.example.data.util.NetworkManager
import com.example.domain.usecase.GetAlbumImageListUseCase
import com.example.domain.usecase.SaveSelectedImagesToServerUseCase
import com.example.multipleimagepicker.viewmodel.ImageDecoViewPagerViewModelFactory
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModel
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModelFactory
import com.example.multipleimagepicker.viewmodel.MainViewModelFactory
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
    fun provideImagePickerViewModelFactory(
        getAlbumImageListUseCase: GetAlbumImageListUseCase
    ) : ImagePickerViewModelFactory{
        return ImagePickerViewModelFactory(getAlbumImageListUseCase)
    }

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        application: Application,
        networkManager: NetworkManager,
        saveSelectedImagesToServerUseCase: SaveSelectedImagesToServerUseCase
    ) : MainViewModelFactory{
        return MainViewModelFactory(
            application,
            networkManager,
            saveSelectedImagesToServerUseCase
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