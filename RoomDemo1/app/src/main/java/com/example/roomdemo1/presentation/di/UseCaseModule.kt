package com.example.roomdemo1.presentation.di

import com.example.roomdemo1.domain.repository.UserRepository
import com.example.roomdemo1.domain.usecase.*
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
    fun provideGetAllUserUseCase(
        userRepository: UserRepository
    ) : GetAllUserUseCase {
        return GetAllUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideAddUserUseCase(
        userRepository: UserRepository
    ) : AddUserUseCase {
        return AddUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteUserUseCase(
        userRepository: UserRepository
    ) : DeleteUserUseCase {
        return DeleteUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(
        userRepository: UserRepository
    ) : UpdateUserUseCase {
        return UpdateUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSelectUserUseCase(
        userRepository: UserRepository
    ) : SelectUserUseCase {
        return SelectUserUseCase(userRepository)
    }
}