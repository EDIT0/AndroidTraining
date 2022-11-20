package com.example.eventbus.otto

import android.app.Activity
import com.squareup.otto.Bus

object OttoEventBus {
    val getOttoObject = Bus()

    fun registerBus(activity: Activity, eventBus : Bus) {
        eventBus.register(activity)
    }

    fun unregisterBus(activity: Activity, eventBus : Bus) {
        eventBus.unregister(activity)
    }

    fun postBus(eventBus: Bus, data: Any) {
        eventBus.post(data)
    }
}