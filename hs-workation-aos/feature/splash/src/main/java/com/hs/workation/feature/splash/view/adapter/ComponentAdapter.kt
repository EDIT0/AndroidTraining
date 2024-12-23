package com.hs.workation.feature.splash.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hs.workation.feature.splash.databinding.ItemComponentBinding

class ComponentAdapter(
    private val onItemClick: (String) -> Unit
) : ListAdapter<String, ComponentAdapter.ComponentViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return ComponentViewHolder(ItemComponentBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ComponentViewHolder(
        private val binding: ItemComponentBinding,
        private val listener: (String) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.root.setOnClickListener {
                listener.invoke(item)
            }
            binding.tvComponentTitle.text = item
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}