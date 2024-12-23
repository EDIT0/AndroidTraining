package com.hs.workation.feature.splash.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hs.workation.feature.splash.databinding.ItemLoadingStateBinding

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<com.hs.workation.feature.splash.view.adapter.LoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: ItemLoadingStateBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 버튼 클릭 시 Fragment 에서 받아온 동작 호출 (retry)
            binding.btnError.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState : LoadState){
            binding.apply {
                progressbar.isVisible = loadState is LoadState.Loading
                btnError.isVisible = loadState is LoadState.Error
                tvError.isVisible = loadState is LoadState.Error
            }
        }
    }


}