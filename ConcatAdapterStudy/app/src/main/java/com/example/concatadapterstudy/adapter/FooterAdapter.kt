package com.example.concatadapterstudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.concatadapterstudy.databinding.FooterListItemBinding
import com.example.concatadapterstudy.databinding.HeaderListItemBinding
import com.example.concatadapterstudy.model.FooterModel

class FooterAdapter : ListAdapter<FooterModel, FooterAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FooterListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: FooterListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FooterModel) {

        }
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