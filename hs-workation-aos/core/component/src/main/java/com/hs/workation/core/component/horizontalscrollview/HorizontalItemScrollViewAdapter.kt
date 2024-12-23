package com.hs.workation.core.component.horizontalscrollview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hs.workation.core.component.databinding.ItemHorizontalScrollBinding
import com.hs.workation.core.component.databinding.ItemSeeMoreBinding
import com.hs.workation.core.model.mock.HorizontalItemScrollAdapterTypeModel
import com.hs.workation.core.model.mock.HorizontalItemScrollAdapterViewHolderType
import com.hs.workation.core.model.mock.HorizontalScrollItem
import com.hs.workation.core.model.mock.SeeMoreItem
import com.hs.workation.core.util.ViewSizeUtil

class HorizontalItemScrollViewAdapter(
    private val onItemClick: (HorizontalScrollItem) -> Unit,
    private val onSeeMoreClick: () -> Unit
) : ListAdapter<HorizontalItemScrollAdapterTypeModel, RecyclerView.ViewHolder>(DiffCallback), HorizontalItemScrollViewAdapterContract {

    // Item view type
    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_SEE_MORE = 1
    }

    // 이미지 디폴트 너비
    private var imageWidth = 200
    // 이미지 디폴트 높이
    private var imageHeight = 200
    // 아이템 간 디폴트 간격
    private var itemToItemMargin = 0

    /**
     * 아이템 ViewHolder
     *
     * @property binding
     * @property listener 아이템 클릭 콜백 리스너
     */
    inner class HorizontalItemScrollViewViewHolder(
        private val binding: ItemHorizontalScrollBinding,
        private val listener: (HorizontalScrollItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HorizontalScrollItem) {
            binding.root.setOnClickListener {
                listener.invoke(item)
            }
            val layoutParams = binding.main.layoutParams
            if (adapterPosition != itemCount - 1) {
                if (layoutParams is ViewGroup.MarginLayoutParams) {
                    layoutParams.marginStart = 0
                    layoutParams.marginEnd = ViewSizeUtil.dpToPx(binding.main.context, itemToItemMargin.toFloat()).toInt()
                    layoutParams.topMargin = 0
                    layoutParams.bottomMargin = 0

                    binding.main.layoutParams = layoutParams
                }
            } else {
                if (layoutParams is ViewGroup.MarginLayoutParams) {
                    layoutParams.marginStart = 0
                    layoutParams.marginEnd = 0
                    layoutParams.topMargin = 0
                    layoutParams.bottomMargin = 0

                    binding.main.layoutParams = layoutParams
                }
            }
            if (item.imageUrl == null) {
                binding.cvImageCard.visibility = View.GONE
            } else {
                binding.cvImageCard.visibility = View.VISIBLE
                Glide.with(binding.ivImage.context)
                    .load(item.imageUrl)
                    .centerCrop()
                    .placeholder(com.hs.workation.core.component.R.drawable.ic_rounded_arrow_circle_right_grey_24)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(imageWidth, imageHeight)
                    .into(binding.ivImage)
            }
            if(item.title == null) {
                binding.tvTitle.visibility = View.GONE
            } else {
                binding.tvTitle.visibility = View.VISIBLE
                binding.tvTitle.text = item.title
            }

        }
    }

    /**
     * 더보기 ViewHolder
     *
     * @property binding
     * @property listener 더보기 클릭 콜백 리스너
     */
    inner class SeeMoreViewHolder(
        private val binding: ItemSeeMoreBinding,
        private val listener: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.setOnClickListener {
                listener.invoke()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<HorizontalItemScrollAdapterTypeModel>() {
        override fun areItemsTheSame(oldItem: HorizontalItemScrollAdapterTypeModel, newItem: HorizontalItemScrollAdapterTypeModel): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HorizontalItemScrollAdapterTypeModel, newItem: HorizontalItemScrollAdapterTypeModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_NORMAL -> {
                HorizontalItemScrollViewViewHolder(ItemHorizontalScrollBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClick)
            }
            TYPE_SEE_MORE -> {
                SeeMoreViewHolder(ItemSeeMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false), onSeeMoreClick)
            }
            else -> {
                throw Exception("onCreateViewHolder Error")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when(currentItem.type) {
            HorizontalItemScrollAdapterViewHolderType.NORMAL -> {
                (holder as HorizontalItemScrollViewViewHolder).bind(getItem(position) as HorizontalScrollItem)
            }
            HorizontalItemScrollAdapterViewHolderType.SEE_MORE -> {
                (holder as SeeMoreViewHolder).bind()
            }
            else -> {
                throw Exception("onBindViewHolder Error")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is HorizontalScrollItem -> {
                TYPE_NORMAL
            }
            is SeeMoreItem -> {
                TYPE_SEE_MORE
            }
            else -> {
                throw Exception("getItemViewType Error")
            }
        }
    }

    override fun setImageSize(width: Int, height: Int) {
        this.imageWidth = width
        this.imageHeight = height
    }

    override fun setItemToItemMargin(margin: Int) {
        this.itemToItemMargin = margin
    }
}