package com.example.roomdemo1.presentation.di

import android.app.Application
import com.example.roomdemo1.domain.usecase.*
import com.example.roomdemo1.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        getAllUserUseCase: GetAllUserUseCase,
        addUserUseCase: AddUserUseCase,
        deleteUserUseCase: DeleteUserUseCase,
        updateUserUseCase: UpdateUserUseCase,
        selectUserUseCase: SelectUserUseCase
    ) : MainViewModel {
        return MainViewModel(
            getAllUserUseCase,
            addUserUseCase,
            deleteUserUseCase,
            updateUserUseCase,
            selectUserUseCase
        )
    }
}