package com.example.pagingdemo1

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagingdemo1.ui.MovieAdapter

object BindingAdapter {
//    @JvmStatic
//    @BindingAdapter("setAdapterItems")
//    fun RecyclerView.setAdapterItems(items: MutableList<MovieModel.MovieModelResult>?) {
//        items?.let {
//            adapter?.let {
//                (it as MovieAdapter).submitList(items.toMutableList())
//            }
//        }
//    }

    @JvmStatic
    @BindingAdapter("setUrlImage")
    fun ImageView.setUrlImage(url: String?) {
        Glide.with(this.context)
            .load(BuildConfig.BASE_MOVIE_POSTER + url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(this)
    }
}