package com.example.roomdemo1.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.roomdemo1.data.db.UserDao
import com.example.roomdemo1.data.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideUserDatabase(app : Application) : UserDatabase {
        return Room.databaseBuilder(app, UserDatabase::class.java, "user_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideGetUserDao(userDatabase: UserDatabase) : UserDao {
        return userDatabase.getUserDao()
    }

}