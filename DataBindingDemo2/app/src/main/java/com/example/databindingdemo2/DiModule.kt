package com.example.databindingdemo2

import android.app.Application
import android.content.Context
import com.example.databindingdemo2.viewmodel.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
//    @Singleton
//    @Provides
//    fun provideContext(@ApplicationContext context: Context) : Context {
//        return context
//    }

    @Provides
    @Singleton
    fun providesMainViewModelFactory() : MainViewModelFactory {
        return MainViewModelFactory()
    }

    @Provides
    @Singleton
    fun providesTextRecyclerViewModelFactory() : TextRecyclerViewModelFactory {
        return TextRecyclerViewModelFactory()
    }

    @Provides
    @Singleton
    fun providesButtonViewModelFactory() : ButtonViewModelFactory {
        return ButtonViewModelFactory()
    }
}