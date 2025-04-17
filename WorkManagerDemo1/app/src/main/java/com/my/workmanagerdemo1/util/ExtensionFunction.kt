package com.my.workmanagerdemo1.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.my.workmanagerdemo1.MyApplication
import com.my.workmanagerdemo1.SharedViewModel

fun Context.getSharedViewModel(): SharedViewModel {
    val app = applicationContext as MyApplication
    return ViewModelProvider(app.appViewModelProvider, ViewModelProvider.AndroidViewModelFactory.getInstance(app))
        .get(SharedViewModel::class.java)
}