package com.example.multipleimagepicker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.ImagePickerModel
import com.example.multipleimagepicker.databinding.ImagePickerItemBinding
import com.example.multipleimagepicker.databinding.SelectedImageItemBinding
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModel

/**
 * @param parentViewModel The ImagePicker's ViewModel which holds each ImageItem
 * whose isChecked should be updated when checkbox checked.
 */
class SelectedImageAdapter() : ListAdapter<ImagePickerModel, SelectedImageAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val _binding = SelectedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
//        val viewHolder = ViewHolder(_binding)
        return ViewHolder(SelectedImageItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: SelectedImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(imagePickerModel: ImagePickerModel) {

//            Log.i("MYTAG", "uri: ${imagePickerModel.uri}")
            Glide.with(binding.root.context)
                .load(imagePickerModel.uri)
                .override(1000, 1000)
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, imagePickerModel)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, ImagePickerModel) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, ImagePickerModel) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * DiffUtil작동 안함
     * */
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<ImagePickerModel>() {
            override fun areContentsTheSame(oldItem: ImagePickerModel, newItem: ImagePickerModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ImagePickerModel, newItem: ImagePickerModel) =
                oldItem.uri == newItem.uri
        }
    }

}