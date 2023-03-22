package com.example.imagesenderdemo1.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.model.SavedImageModel
import com.example.imagesenderdemo1.databinding.SavedImageItemBinding

class SavedImageAdapter : ListAdapter<SavedImageModel, SavedImageAdapter.ViewHolder>(diffUtil){

    private lateinit var savedImageItemBinding: SavedImageItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        savedImageItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.saved_image_item, parent, false)
        return ViewHolder(savedImageItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun replaceItems(items: List<SavedImageModel>) {
        submitList(items)
    }


    inner class ViewHolder(val binding: SavedImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SavedImageModel) {
            binding.savedImageModel = data

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(binding, adapterPosition, data)
                }
            }
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    private var onItemClickListener : ((SavedImageItemBinding, Int, SavedImageModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (SavedImageItemBinding, Int, SavedImageModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<SavedImageModel>() {
            override fun areContentsTheSame(oldItem: SavedImageModel, newItem: SavedImageModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: SavedImageModel, newItem: SavedImageModel) =
                oldItem.idx == newItem.idx
        }
    }
}