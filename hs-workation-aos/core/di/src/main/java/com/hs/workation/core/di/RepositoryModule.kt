package com.hs.workation.core.di

import com.hs.workation.data.datasource.remote.AuthRemoteDataSource
import com.hs.workation.data.datasource.remote.TestRemoteDataSource
import com.hs.workation.data.repository.AuthRepositoryImpl
import com.hs.workation.data.repository.TestRepositoryImpl
import com.hs.workation.domain.repository.AuthRepository
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

    @Singleton
    @Provides
    fun providesAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }
}