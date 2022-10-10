package com.example.pagingdemo1.di

import com.example.pagingdemo1.repo.remote.RemoteDataSource
import com.example.pagingdemo1.repo.remote.RemoteDataSourceImpl
import com.example.pagingdemo1.network.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(service: Service) : RemoteDataSource {
        return RemoteDataSourceImpl(service)
    }

}