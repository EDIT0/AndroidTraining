package com.example.pagingdemo1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingdemo1.model.MovieModel
import com.example.pagingdemo1.databinding.MovieListItemBinding

class MovieAdapter : PagingDataAdapter<MovieModel.MovieModelResult, MovieAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(popularMovieModelResult: MovieModel.MovieModelResult) {

            binding.movieListItem = popularMovieModelResult
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, popularMovieModelResult)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, MovieModel.MovieModelResult) -> Unit)? = null

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