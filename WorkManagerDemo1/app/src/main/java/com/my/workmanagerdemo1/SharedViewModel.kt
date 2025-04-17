package com.my.workmanagerdemo1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * 앱 공통 ViewModel
 */
class SharedViewModel(application: Application) : AndroidViewModel(application) {

    var data1 = "data1"
    var data2 = "data2"

    var commonSharedFlow = MutableSharedFlow<String>(replay = 2)


}