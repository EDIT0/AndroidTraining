package com.example.databindingdemo2

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("visible", "animType", "animDuration", requireAll = true)
    fun setOnOffButton(view: Button, visible: Boolean, animType: Int, animDuration: Int) {
        if(visible) {
            if(view.visibility == View.INVISIBLE) {
                val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_in)
                animation.duration = animDuration.toLong()
                animation.fillAfter = true
                view.startAnimation(animation)
                view.visibility = View.VISIBLE
            }
        } else {
            if(view.visibility == View.VISIBLE) {
                val animation: Animation = AnimationUtils.loadAnimation(view.context, R.anim.fade_out)
                animation.duration = animDuration.toLong()
                animation.fillAfter = true
                view.startAnimation(animation)
                view.visibility = View.INVISIBLE
            }
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("visible")
    fun setOnOffButton2(view: Button, visible: Boolean) {
        if(visible) {
            Log.i("MYTAG", "트루여서 보여줌")
            view.visibility = View.VISIBLE
        } else {
            Log.i("MYTAG", "폴스라서 안보여줌")
            view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("customText")
    fun setCustomText(view: TextView, text: String) {
        view.text = text
    }
}