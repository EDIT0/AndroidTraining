package com.example.concatadapterstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.R
import com.example.concatadapterstudy.databinding.HeaderListItemBinding
import com.example.concatadapterstudy.model.HeaderModel

class HeaderAdapter : ListAdapter<HeaderModel, HeaderAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HeaderListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: HeaderListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HeaderModel) {

            binding.tvHeaderTitle.text = "HeaderAdapter " + data.headerText

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, data)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, HeaderModel) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, HeaderModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.header_list_item
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<HeaderModel>() {
            override fun areContentsTheSame(oldItem: HeaderModel, newItem: HeaderModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: HeaderModel, newItem: HeaderModel) =
                oldItem.headerText == newItem.headerText
        }
    }

}