package com.example.fragmentviewmodelexample1.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentviewmodelexample1.R
import com.example.fragmentviewmodelexample1.databinding.ListItemBinding
import com.example.fragmentviewmodelexample1.model.ItemModel

class ItemAdapter : ListAdapter<ItemModel, ItemAdapter.ViewHolder>(diffUtil) {

    private lateinit var listItemBinding: ListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        listItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_item, parent, false)
        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ItemModel) {
            binding.itemModel = data
            binding.tvName.text = "$adapterPosition ${data.name}"

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, data)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, ItemModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (Int, ItemModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<ItemModel>() {
            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel) =
                oldItem.name == newItem.name
        }
    }

}