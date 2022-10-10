package com.example.pagingdemo1.di

import com.example.pagingdemo1.repo.Repository
import com.example.pagingdemo1.usecase.GetPopularMovieUseCase
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
    fun provideGetPopularMoviesUseCase(
        repository: Repository
    ): GetPopularMovieUseCase {
        return GetPopularMovieUseCase(repository)
    }
}