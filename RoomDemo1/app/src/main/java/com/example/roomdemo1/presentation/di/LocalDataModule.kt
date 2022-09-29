package com.example.roomdemo1.presentation.di

import com.example.roomdemo1.data.db.UserDao
import com.example.roomdemo1.data.repository.local.UserLocalDataSource
import com.example.roomdemo1.data.repository.local.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideUserLocalDataSource(userDao: UserDao) : UserLocalDataSource {
        return UserLocalDataSourceImpl(userDao)
    }
}