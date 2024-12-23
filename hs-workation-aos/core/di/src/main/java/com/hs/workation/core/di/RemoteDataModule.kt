package com.hs.workation.core.di

import com.hs.workation.data.apiservice.TestService
import com.hs.workation.data.datasource.remote.TestRemoteDataSource
import com.hs.workation.data.datasource.remote.impl.TestRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun providesTestRemoteDataSource(
        testService: TestService
    ): TestRemoteDataSource {
        return TestRemoteDataSourceImpl(testService)
    }
}