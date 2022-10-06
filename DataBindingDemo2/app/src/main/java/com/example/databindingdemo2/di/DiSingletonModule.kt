package com.example.databindingdemo2.di

import com.example.databindingdemo2.viewmodel.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiSingletonModule {
//    @Singleton
//    @Provides
//    fun provideContext(@ApplicationContext context: Context) : Context {
//        return context
//    }

    @Provides
    @Singleton
    fun providesInputEditTextViewModelFactory() : InputEditTextViewModelFactory {
        return InputEditTextViewModelFactory()
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