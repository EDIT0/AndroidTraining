package com.example.eventbus.greenrobot

import android.util.Log
import androidx.lifecycle.ViewModel
import com.squareup.otto.Bus
import org.greenrobot.eventbus.EventBus

class TwoViewModel(

) : ViewModel() {
    private var count = 0

    // 이벤트버스 실행
    fun produceEvent(eventBus : EventBus) {
        count++
        Log.i("MYTAG", "produceEvent()")
        GreenRobotEventBus.postBus(eventBus, "${count}")
    }
}