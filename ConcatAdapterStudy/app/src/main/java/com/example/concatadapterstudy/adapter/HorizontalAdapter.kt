package com.example.concatadapterstudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.R
import com.example.concatadapterstudy.databinding.HorizontalListItemBinding
import com.example.concatadapterstudy.model.HorizontalModel

class HorizontalAdapter : ListAdapter<HorizontalModel, HorizontalAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HorizontalListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: HorizontalListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: HorizontalModel) {

            binding.tvHorizontalTitle.text = "HorizontalAdapter " + data.horizontalText

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, data)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, HorizontalModel) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, HorizontalModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.horizontal_list_item
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<HorizontalModel>() {
            override fun areContentsTheSame(oldItem: HorizontalModel, newItem: HorizontalModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: HorizontalModel, newItem: HorizontalModel) =
                oldItem.horizontalText == newItem.horizontalText
        }
    }

}