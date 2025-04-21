package com.my.localstoragedemo1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.localstoragedemo1.databinding.ItemFileBinding

class FileListAdapter(
    val onItemClick: (String) -> Unit
): ListAdapter<String, FileListAdapter.FileListViewHolder>(diffUtil) {

    inner class FileListViewHolder(
        val binding: ItemFileBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: String) {
            binding.tvFileName.text = data

            binding.root.setOnClickListener {
                onItemClick.invoke(data)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileListViewHolder {
        return FileListViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FileListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}