package com.example.eventbus.greenrobot

import android.app.Activity
import org.greenrobot.eventbus.EventBus

object GreenRobotEventBus {
    val getGreenRobotObject = EventBus.getDefault()

    fun registerBus(activity: Activity, eventBus : EventBus) {
        eventBus.register(activity)
    }

    fun unregisterBus(activity: Activity, eventBus : EventBus) {
        eventBus.unregister(activity)
    }

    fun postBus(eventBus: EventBus, data: Any) {
        eventBus.post(data)
    }
}