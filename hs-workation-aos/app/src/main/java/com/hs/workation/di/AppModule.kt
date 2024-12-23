package com.hs.workation.di

import com.hs.workation.navigation.NavActivityImpl
import com.hs.workation.core.common.navigation.NavActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesNavActivityImpl(): NavActivityImpl {
        return NavActivityImpl()
    }

    @Singleton
    @Provides
    fun providesNavActivity(navActivityImpl: NavActivityImpl): NavActivity = navActivityImpl
}