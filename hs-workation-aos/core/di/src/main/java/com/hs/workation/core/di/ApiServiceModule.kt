package com.hs.workation.core.di

import com.hs.workation.data.apiservice.TestService
import com.hs.workation.data.apiservice.impl.TestServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun providesTestService(): TestService {
        return TestServiceImpl()
    }

}