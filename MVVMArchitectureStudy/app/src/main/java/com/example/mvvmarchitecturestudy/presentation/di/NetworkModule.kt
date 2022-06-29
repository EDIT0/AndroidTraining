package com.example.mvvmarchitecturestudy.presentation.di

import com.example.mvvmarchitecturestudy.BuildConfig
import com.example.mvvmarchitecturestudy.data.api.TmdbAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideTmdbAPIService(retrofit: Retrofit) : TmdbAPIService {
        return retrofit.create(TmdbAPIService::class.java)
    }
}