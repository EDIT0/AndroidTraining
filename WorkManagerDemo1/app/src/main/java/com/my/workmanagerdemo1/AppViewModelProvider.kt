package com.my.workmanagerdemo1

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class AppViewModelProvider : ViewModelStoreOwner {

    private val appViewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore
}