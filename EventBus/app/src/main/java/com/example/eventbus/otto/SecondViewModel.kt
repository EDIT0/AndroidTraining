package com.example.eventbus.otto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.otto.Bus
import kotlinx.coroutines.launch

class SecondViewModel(

) : ViewModel() {
    private var count = 0

    // 이벤트버스 실행
    fun produceEvent(eventBus : Bus) {
        count++
        Log.i("MYTAG", "produceEvent()")
        OttoEventBus.postBus(eventBus, "${count}")
    }
}