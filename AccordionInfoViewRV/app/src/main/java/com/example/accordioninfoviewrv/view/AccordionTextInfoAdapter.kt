package com.example.accordioninfoviewrv.view

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.accordioninfoviewrv.R
import com.example.accordioninfoviewrv.databinding.ItemAccordionTextInfoBinding

class AccordionTextInfoAdapter: ListAdapter<AccordionTextInfoModel, AccordionTextInfoAdapter.TextInfoViewHolder>(diffUtil), BaseAccordionTextInfoView {

    private val expandableViewRealHeight = ArrayList<Int>()

    private var titleTextSize : Int = 18
    private var contentsTextSize : Int = 15
    private var titleTextColor : Int = R.color.black
    private var contentsTextColor : Int = R.color.black
    private var iconWidth: Int? = null
    private var iconHeight: Int? = null
    private var icon : Int = R.drawable.ic_arrow_right_40

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccordionTextInfoAdapter.TextInfoViewHolder {
        return TextInfoViewHolder(ItemAccordionTextInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TextInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class TextInfoViewHolder(val binding: ItemAccordionTextInfoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AccordionTextInfoModel) {
            binding.apply {
                tvCollapsedTitle.apply {
                    text = data.title
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize.toFloat())
                    setTextColor(ContextCompat.getColor(context, titleTextColor))
                }
                tvContents.apply {
                    text = data.contents
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, contentsTextSize.toFloat())
                    setTextColor(ContextCompat.getColor(context, contentsTextColor))
                }

                if (iconWidth != null) {
                    val iconLayoutParams = ivCollapsedDirection.layoutParams
                    iconLayoutParams.width = Util.dpToPx(ivCollapsedDirection.context, iconWidth!!.toFloat())
                    ivCollapsedDirection.layoutParams = iconLayoutParams
                    Log.d("MYTAG", "iconWidth: ${iconWidth!!.toFloat()}")
                }
                if(iconHeight != null) {
                    val iconLayoutParams = ivCollapsedDirection.layoutParams
                    iconLayoutParams.height = Util.dpToPx(ivCollapsedDirection.context, iconHeight!!.toFloat())
                    ivCollapsedDirection.layoutParams = iconLayoutParams
                    Log.d("MYTAG", "iconHeight: ${iconHeight!!.toFloat()}")
                }

                Glide.with(binding.ivCollapsedDirection.context)
                    .load(icon)
                    .error(R.drawable.ic_arrow_right_40)
                    .into(binding.ivCollapsedDirection)

                toggleLayout(false, binding.rootLayout, binding.expandableView, 0) // 처음 상태는 접기

                edgeView.setOnClickListener {
                    if(binding.expandableView.visibility == View.VISIBLE) {
                        ToggleAnimation.toggleArrow(binding.ivCollapsedDirection, false)
                        toggleLayout(false, binding.rootLayout, binding.expandableView, absoluteAdapterPosition)
                    } else {
                        ToggleAnimation.toggleArrow(binding.ivCollapsedDirection, true)
                        toggleLayout(true, binding.rootLayout, binding.expandableView, absoluteAdapterPosition)
                    }
                }
            }

            binding.expandableView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    Log.d("MYTAG", "각 높이 초기화: ${binding.expandableView.measuredHeight}")
                    expandableViewRealHeight.add(binding.expandableView.measuredHeight)
                    binding.expandableView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: ConstraintLayout, position: Int): Boolean {
        if (isExpanded) {
            Log.d("MYTAG", "측정된 높이 : ${expandableViewRealHeight[position]}")
            ToggleAnimation.expand(layoutExpand, expandableViewRealHeight[position])
        } else {
            ToggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }

    private var onItemClickListener : ((AccordionTextInfoModel) -> Unit)? = null

    fun setOnItemClickListener(listener : (AccordionTextInfoModel) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<AccordionTextInfoModel>() {
            override fun areContentsTheSame(oldItem: AccordionTextInfoModel, newItem: AccordionTextInfoModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: AccordionTextInfoModel, newItem: AccordionTextInfoModel) =
                oldItem.contents == newItem.contents
        }
    }

    override fun setTitleTextSize(sizeSp: Int) {
        titleTextSize = sizeSp
    }

    override fun setContentsTextSize(sizeSp: Int) {
        contentsTextSize = sizeSp
    }

    override fun setTitleTextColor(color: Int) {
        titleTextColor = color
    }

    override fun setContentsTextColor(color: Int) {
        contentsTextColor = color
    }

    override fun setIconSize(widthDp: Int?, heightDp: Int?) {
        widthDp?.let {
            if(it > 40) {
                iconWidth = 40
            } else if(it <= 0) {
                iconWidth = null
            } else {
                iconWidth = widthDp
            }
        }
        heightDp?.let {
            if(it > 40) {
                iconHeight = 40
            } else if(it <= 0) {
                iconHeight = null
            } else {
                iconHeight = heightDp
            }
        }
    }

    override fun setIcon(imgDrawable: Int) {
        icon = imgDrawable
    }
}