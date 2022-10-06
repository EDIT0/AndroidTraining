package com.example.databindingdemo2.di

import com.example.databindingdemo2.adapter.TextAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DiActivityModule {

    @Provides
    fun providesTextAdapter() : TextAdapter {
        return TextAdapter()
    }
}