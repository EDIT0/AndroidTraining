package com.example.fragmentviewmodelexample1.di

import android.app.Application
import com.example.fragmentviewmodelexample1.ui.viewmodel.AViewModel
import com.example.fragmentviewmodelexample1.ui.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun providesMainViewModel(
        application: Application
    ) : MainViewModel {
        return MainViewModel(application)
    }

    @Singleton
    @Provides
    fun providesAViewModel(
        application: Application
    ) : AViewModel {
        return AViewModel(application)
    }
}