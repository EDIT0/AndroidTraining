package com.my.customviewdemo1.view.xml

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.customviewdemo1.R
import com.my.customviewdemo1.ViewSizeUtil
import com.my.customviewdemo1.databinding.ItemDaySelectionBoxBinding
import com.my.customviewdemo1.dto.DaySelectionModel

class DaySelectionHorizontalScrollAdapter: ListAdapter<DaySelectionModel, DaySelectionHorizontalScrollAdapter.DaySelectionHorizontalScrollViewHolder>(DiffCallback) {

    interface DayClickListener {
        fun onDayClicked(year: Int, month: Int, day: Int)
    }

    private var dayClickListener: DayClickListener? = null

    private var selectedBgDrawable: Int = R.drawable.bg_day_selection_box_on
    private var unselectedBgDrawable: Int = R.drawable.bg_day_selection_box_off
    private var selectedTextColor: Int = R.color.white
    private var unselectedTextColor: Int = R.color.grey_500

    fun setDayClickCallback(clickListener: DayClickListener) {
        this.dayClickListener = clickListener
    }

    fun setDayBgAndTextColor(selectedBgDrawable: Int, unselectedBgDrawable: Int, selectedTextColor: Int, unselectedTextColor: Int) {
        this.selectedBgDrawable = selectedBgDrawable
        this.unselectedBgDrawable = unselectedBgDrawable
        this.selectedTextColor = selectedTextColor
        this.unselectedTextColor = unselectedTextColor
    }

    inner class DaySelectionHorizontalScrollViewHolder(
        private val binding: ItemDaySelectionBoxBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DaySelectionModel, totalCount: Int, dayClickListener: DayClickListener?) {
            binding.tvDay.text = "${item.day}"

            binding.dayLayout.setOnClickListener {
                dayClickListener?.onDayClicked(item.year, item.month, item.day)
            }

            if(item.isClicked) {
                binding.dayLayout.setBackgroundResource(selectedBgDrawable)
                binding.tvDay.setTextColor(ContextCompat.getColor(binding.tvDay.context, selectedTextColor))
            } else {
                binding.dayLayout.setBackgroundResource(unselectedBgDrawable)
                binding.tvDay.setTextColor(ContextCompat.getColor(binding.tvDay.context, unselectedTextColor))
            }

            if(totalCount-1 != adapterPosition) {
                val marginLayoutParams = binding.dayLayout.layoutParams as MarginLayoutParams
                marginLayoutParams.setMargins(0, 0, ViewSizeUtil.dpToPx(context = binding.dayLayout.context, 9f).toInt(), 0)
                binding.dayLayout.layoutParams = marginLayoutParams
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<DaySelectionModel>() {
        override fun areItemsTheSame(oldItem: DaySelectionModel, newItem: DaySelectionModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DaySelectionModel, newItem: DaySelectionModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaySelectionHorizontalScrollViewHolder {
        return DaySelectionHorizontalScrollViewHolder(ItemDaySelectionBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DaySelectionHorizontalScrollViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemCount, dayClickListener)
        }
    }
}