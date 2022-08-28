package com.example.concatadapterstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.concatadapterstudy.model.MovieModel
import com.example.concatadapterstudy.databinding.BodyListItemBinding

class BodyAdapter : ListAdapter<MovieModel.MovieModelResult, BodyAdapter.ViewHolder>(diffUtil) {

    private val BASE_MOVIE_POSTER = "https://image.tmdb.org/t/p/original/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(BodyListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: BodyListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(popularMovieModelResult: MovieModel.MovieModelResult) {

            Glide.with(binding.ivThumbnail.context)
                .load(BASE_MOVIE_POSTER + popularMovieModelResult.posterPath)
                .into(binding.ivThumbnail)

            binding.tvTitle.text = popularMovieModelResult.title
            binding.tvReleaseDate.text = popularMovieModelResult.releaseDate

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, popularMovieModelResult)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, MovieModel.MovieModelResult) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, MovieModel.MovieModelResult) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MovieModel.MovieModelResult>() {
            override fun areContentsTheSame(oldItem: MovieModel.MovieModelResult, newItem: MovieModel.MovieModelResult) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MovieModel.MovieModelResult, newItem: MovieModel.MovieModelResult) =
                oldItem.id == newItem.id
        }
    }

}