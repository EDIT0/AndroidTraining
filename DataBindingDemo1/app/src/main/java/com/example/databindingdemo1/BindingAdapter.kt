package com.example.databindingdemo1

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindingAdapter {
    @BindingAdapter("setNumberText")
    @JvmStatic
    fun setNumberText(view: TextView, text: Int){
        view.text = "제 번호는 ${text}입니다."
    }

    @BindingAdapter("setImgProfile", "error")
    @JvmStatic
    fun setImgProfile(view: ImageView, path: String, error: Drawable){
        Glide.with(view.context)
            .load(path)
            .error(error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(view)
    }
}