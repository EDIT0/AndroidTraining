package com.example.pagingdemo1.di

import com.example.pagingdemo1.repo.remote.RemoteDataSource
import com.example.pagingdemo1.repo.Repository
import com.example.pagingdemo1.repo.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ) : Repository {
        return RepositoryImpl(
            remoteDataSource
        )
    }
}