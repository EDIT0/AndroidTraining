package com.example.concatadapterstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.R
import com.example.concatadapterstudy.databinding.FooterListItemBinding
import com.example.concatadapterstudy.databinding.HeaderListItemBinding
import com.example.concatadapterstudy.model.FooterModel
import com.example.concatadapterstudy.model.HeaderModel

class FooterAdapter : ListAdapter<FooterModel, FooterAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FooterListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: FooterListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FooterModel) {
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, data)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, FooterModel) -> Unit) ?= null

    fun setOnItemClickListener(listener : (Int, FooterModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.footer_list_item
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<FooterModel>() {
            override fun areContentsTheSame(oldItem: FooterModel, newItem: FooterModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: FooterModel, newItem: FooterModel) =
                oldItem.state == newItem.state
        }
    }

}