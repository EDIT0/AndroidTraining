package com.edit.skeletonloading

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edit.skeletonloading.databinding.ItemBinding

class Adapter : ListAdapter<Model, Adapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Model) {

            Glide.with(binding.ivThumbnail.context)
                .load(model.thumbnail)
                .into(binding.ivThumbnail)

            binding.tvName.text = model.name
            binding.tvTitle.text = model.title
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Model>() {
            override fun areContentsTheSame(oldItem: Model, newItem: Model) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Model, newItem: Model) =
                oldItem.idx == newItem.idx
        }
    }
}