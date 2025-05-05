package com.my.dfmdemo1

import android.app.Application
import com.my.dfmdemo1.core.navigation.CountryRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        CountryRepository.init(this)
    }
}