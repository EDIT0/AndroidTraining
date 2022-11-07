package com.example.mvcexample1.di

import com.example.mvcexample1.model.Movie
import com.example.mvcexample1.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModelModule {

    @Provides
    @Singleton
    fun provideMovie(apiService: ApiService) : Movie {
        return Movie(apiService)
    }

}