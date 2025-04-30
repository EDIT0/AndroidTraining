package com.my.dfmdemo1

import android.app.Application
import com.my.dfmdemo1.core.navigation.CountryRepository

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        CountryRepository.init(this)
    }
}