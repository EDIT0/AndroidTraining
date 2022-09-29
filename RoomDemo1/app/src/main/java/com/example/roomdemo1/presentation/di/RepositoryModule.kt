package com.example.roomdemo1.presentation.di

import com.example.roomdemo1.data.repository.UserRepositoryImpl
import com.example.roomdemo1.data.repository.local.UserLocalDataSource
import com.example.roomdemo1.domain.repository.UserRepository
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
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource
    ) : UserRepository {
        return UserRepositoryImpl(userLocalDataSource)
    }

}