package com.my.dfmdemo1

import com.my.dfmdemo1.core.navigation.ActivityNavigator
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
    fun providesActivityNavigatorImpl(): ActivityNavigatorImpl {
        return ActivityNavigatorImpl()
    }

    @Singleton
    @Provides
    fun providesActivityNavigator(
        activityNavigatorImpl: ActivityNavigatorImpl
    ): ActivityNavigator {
        return activityNavigatorImpl
    }

}