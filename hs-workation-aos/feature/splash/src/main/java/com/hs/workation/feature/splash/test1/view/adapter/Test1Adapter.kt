package com.hs.workation.feature.splash.test1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hs.workation.core.model.test.res.ResTest2
import com.hs.workation.feature.splash.databinding.ItemTest1Binding

class Test1Adapter : PagingDataAdapter<ResTest2.TestQuestion, Test1Adapter.ViewHolder>(
    diffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTest1Binding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val binding: ItemTest1Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(testQuestion: ResTest2.TestQuestion) {

            binding.resTest2Item = testQuestion
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(adapterPosition, testQuestion)
                }
            }
        }
    }

    private var onItemClickListener : ((Int, ResTest2.TestQuestion) -> Unit)? = null

    fun setOnItemClickListener(listener : (Int, ResTest2.TestQuestion) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<ResTest2.TestQuestion>() {
            override fun areContentsTheSame(oldItem: ResTest2.TestQuestion, newItem: ResTest2.TestQuestion) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ResTest2.TestQuestion, newItem: ResTest2.TestQuestion) =
                oldItem.title == newItem.title
        }
    }

}