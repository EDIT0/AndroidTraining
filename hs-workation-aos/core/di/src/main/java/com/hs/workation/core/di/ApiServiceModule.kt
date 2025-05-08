package com.hs.workation.core.di

import com.hs.workation.core.common.constants.NetworkConstants.AUTH_RETROFIT
import com.hs.workation.core.common.constants.NetworkConstants.BASE_RETROFIT
import com.hs.workation.data.apiservice.AuthApiService
import com.hs.workation.data.apiservice.BaseApiService
import com.hs.workation.data.apiservice.TestService
import com.hs.workation.data.apiservice.impl.TestServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun providesTestService(): TestService {
        return TestServiceImpl()
    }

    @Singleton
    @Provides
    fun providesAuthApiService(@Named(AUTH_RETROFIT) retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesBaseApiService(@Named(BASE_RETROFIT) retrofit: Retrofit): BaseApiService {
        return retrofit.create(BaseApiService::class.java)
    }
}