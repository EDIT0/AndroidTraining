package com.example.pagingdemo1.di

import com.example.pagingdemo1.ui.MainViewModelFactory
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FactoryModule {

    @Singleton
    @Provides
    fun providesMainViewModelFactory(
        getPopularMovieUseCase: GetPopularMovieUseCase
    ) : MainViewModelFactory {
        return MainViewModelFactory(
            getPopularMovieUseCase
        )
    }
}