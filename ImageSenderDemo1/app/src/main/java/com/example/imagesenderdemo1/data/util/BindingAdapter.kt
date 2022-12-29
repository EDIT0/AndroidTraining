package com.example.imagesenderdemo1.data.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setUrl", "error", requireAll = true)
    fun setImage(imageView: ImageView, url: String?, error: Drawable) {
        url?.let {
            Glide.with(imageView.context)
                .load(url)
                .error(error)
                .fallback(error)
                .into(imageView)
        }
    }
}