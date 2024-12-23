package com.my.imagegallerydemo1

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.imagegallerydemo1.databinding.ItemImageBinding

class ImageAdapter(
    private val onItemClick: (Image) -> Unit,
    private val onItemDeleteClick: (Image) -> Unit
) : ListAdapter<Image, ImageAdapter.ImageViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: android.view.ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick, onItemDeleteClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val onItemClick: (Image) -> Unit,
        private val onItemDeleteClick: (Image) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Image) {
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
            binding.ivImageDelete.setOnClickListener {
                onItemDeleteClick.invoke(item)
            }
            binding.ivImage.setImageURI(item.uri)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}