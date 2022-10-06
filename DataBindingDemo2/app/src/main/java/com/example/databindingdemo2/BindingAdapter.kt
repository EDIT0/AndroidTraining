package com.example.databindingdemo2

import androidx.databinding.BindingAdapter
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible", "animType", "animDuration", requireAll = true)
    fun setOnOffButton(view: Button, visible: Boolean, @AnimRes animType: Int, animDuration: Int) {
        if (visible) {
            if (view.visibility == View.GONE) {
                val animation: Animation = AnimationUtils.loadAnimation(view.context, animType)
                animation.duration = animDuration.toLong()
                animation.fillAfter = true
                view.startAnimation(animation)
                view.visibility = View.VISIBLE
            }
        } else {
            if (view.visibility == View.VISIBLE) {
                val animation: Animation = AnimationUtils.loadAnimation(view.context, animType)
                animation.duration = animDuration.toLong()
                animation.fillAfter = true
                view.startAnimation(animation)
                view.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setOnOffButton2(view: Button, visible: Boolean) {
        if (visible) {
            Log.i("MYTAG", "트루여서 보여줌")
            view.visibility = View.VISIBLE
        } else {
            Log.i("MYTAG", "폴스라서 안보여줌")
            view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("setCustomText")
    fun setCustomText(view: TextView, text: String) {
        view.text = text
    }

//    @JvmStatic
//    @BindingAdapter("adapter", "layoutManager")
//    fun setRVTextAdapter(view: RecyclerView, adapter: TextAdapter, layoutManager: LinearLayoutManager) {
//        view.apply {
//            this.adapter = adapter
//            this.layoutManager = layoutManager
//        }
//    }

    @JvmStatic
    @BindingAdapter("setAdapterItems")
    fun RecyclerView.setAdapterItems(items: MutableList<TextModel>?) {
        items?.let {
//            Log.i("MYTAG", "1")
            adapter?.let {
//                Log.i("MYTAG", "2")
                (it as TextAdapter).submitList(items.toMutableList())
//                Log.i("MYTAG", "3")
            }
        }
    }
}