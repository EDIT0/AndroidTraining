package com.my.xmlnavigationdemo1.bottomnav.fragment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class AOneViewModel: ViewModel() {

    var scrollPosition: Int = 0
    var etText: String = ""

    override fun onCleared() {
        super.onCleared()
        Log.i("MYTAG ${javaClass.simpleName}", "onCleared()")
    }
}