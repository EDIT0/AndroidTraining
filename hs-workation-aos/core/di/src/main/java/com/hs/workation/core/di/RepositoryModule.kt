package com.hs.workation.core.di

import com.hs.workation.data.datasource.remote.TestRemoteDataSource
import com.hs.workation.data.repository.TestRepositoryImpl
import com.hs.workation.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesTestRepository(
        testRemoteDataSource: TestRemoteDataSource
    ): TestRepository {
        return TestRepositoryImpl(testRemoteDataSource)
    }
}