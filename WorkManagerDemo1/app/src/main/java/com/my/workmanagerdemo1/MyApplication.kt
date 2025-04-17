package com.my.workmanagerdemo1

import android.app.Application

class MyApplication : Application() {

    lateinit var appViewModelProvider: AppViewModelProvider
        private set

    override fun onCreate() {
        super.onCreate()
        appViewModelProvider = AppViewModelProvider()
    }
}