package com.example.databindingdemo2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databindingdemo2.databinding.TextListItemBinding
import com.example.databindingdemo2.model.TextModel

class TextAdapter : ListAdapter<TextModel, TextAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: TextListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(textModel: TextModel) {

            binding.textModel = textModel

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, textModel)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, TextModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (Int, TextModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<TextModel>() {
            override fun areContentsTheSame(oldItem: TextModel, newItem: TextModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: TextModel, newItem: TextModel) =
                oldItem.word == newItem.word
        }
    }
}