package com.example.imagesenderdemo1.data.util

import android.app.Activity
import org.greenrobot.eventbus.EventBus

object EventBus {
    fun registerBus(mActivity : Activity) {
        EventBus.getDefault().register(mActivity)
    }

    fun unregisterBus(mActivity: Activity) {
        EventBus.getDefault().unregister(mActivity)
    }

    fun post(data: Any) {
        EventBus.getDefault().post(data)
    }
}