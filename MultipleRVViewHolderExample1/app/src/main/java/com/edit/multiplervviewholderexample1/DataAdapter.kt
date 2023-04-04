package com.edit.multiplervviewholderexample1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edit.multiplervviewholderexample1.databinding.FirstItemBinding
import com.edit.multiplervviewholderexample1.databinding.LoadingItemBinding
import com.edit.multiplervviewholderexample1.databinding.SecondItemBinding
import com.edit.multiplervviewholderexample1.databinding.ThirdItemBinding
import com.edit.multiplervviewholderexample1.model.*

class DataAdapter: ListAdapter<Model, RecyclerView.ViewHolder>(diffUtil) {

    inner class FirstViewHolder(val binding: FirstItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FirstModel) {
            binding.tvFirst.text = data.firstData
        }
    }

    inner class SecondViewHolder(val binding: SecondItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SecondModel) {
            binding.tvSecond.text = data.secondData
        }
    }

    inner class ThirdViewHolder(val binding: ThirdItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ThirdModel) {
            binding.tvThird.text = data.thirdData
        }
    }

    inner class LoadingViewHolder(val binding: LoadingItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: LoadingModel) {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is FirstModel -> {
                TYPE_FIRST
            }
            is SecondModel -> {
                TYPE_SECOND
            }
            is ThirdModel -> {
                TYPE_THIRD
            }
            is LoadingModel -> {
                TYPE_LOADING
            }
            else -> {
                throw Exception("Error")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_FIRST -> {
                FirstViewHolder(FirstItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            TYPE_SECOND -> {
                SecondViewHolder(SecondItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            TYPE_THIRD -> {
                ThirdViewHolder(ThirdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            TYPE_LOADING -> {
                LoadingViewHolder(LoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                throw Exception("Error")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when(currentItem.type) {
            ViewHolderType.FIRST -> {
                (holder as FirstViewHolder).bind(getItem(position) as FirstModel)
            }
            ViewHolderType.SECOND -> {
                (holder as SecondViewHolder).bind(getItem(position) as SecondModel)
            }
            ViewHolderType.THIRD -> {
                (holder as ThirdViewHolder).bind(getItem(position) as ThirdModel)
            }
            ViewHolderType.LOADING -> {
                (holder as LoadingViewHolder).bind(getItem(position) as LoadingModel)
            }
            else -> {
                throw Exception("Error")
            }
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Model>() {
            override fun areContentsTheSame(oldItem: Model, newItem: Model) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Model, newItem: Model) =
                oldItem.idx == newItem.idx
        }

        private const val TYPE_FIRST = 0
        private const val TYPE_SECOND = 1
        private const val TYPE_THIRD = 2
        private const val TYPE_LOADING = 3
    }

}