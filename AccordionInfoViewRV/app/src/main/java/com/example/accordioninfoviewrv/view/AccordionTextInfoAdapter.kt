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

class AccordionTextInfoAdapter: ListAdapter<AccordionTextInfoModel, AccordionTextInfoAdapter.TextInfoViewHolder>(diffUtil), BaseAccordionTextInfoView.AccordionTextInfoAdapter {

    private val expandableViewRealHeight = ArrayList<Int>()

    private var titleTextSize : Int = 18
    private var contentsTextSize : Int = 15
    private var titleTextColor : Int = R.color.black
    private var contentsTextColor : Int = R.color.black
    private var contentsTextBackgroundColor : Int = android.R.color.transparent
    private var iconWidth: Int? = null
    private var iconHeight: Int? = null
    private var isShowLine: Boolean = false
    private var lineColor: Int = R.color.black
    private var lineHeight: Float = 1f
    private var icon : Int = R.drawable.ic_arrow_right_40

    private val maxIconSize = 30

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
                // edgeView의 Top 라인 높이 및 색깔 설정
                val topLineParams = topLine.layoutParams as ConstraintLayout.LayoutParams
                topLineParams.height = Util.dpToPx(topLine.context, lineHeight)
                topLine.layoutParams = topLineParams
                topLine.setBackgroundColor(ContextCompat.getColor(topLine.context, lineColor))

                // edgeView의 Bottom 라인 높이 및 색깔 설정
                val bottomLineParams = bottomLine.layoutParams as ConstraintLayout.LayoutParams
                bottomLineParams.height = Util.dpToPx(bottomLine.context, lineHeight)
                bottomLine.layoutParams = bottomLineParams
                bottomLine.setBackgroundColor(ContextCompat.getColor(topLine.context, lineColor))

                // 가장 하단 Last 라인 높이 및 색깔 설정
                val lastLineParams = lastLine.layoutParams as ConstraintLayout.LayoutParams
                lastLineParams.height = Util.dpToPx(lastLine.context, lineHeight)
                lastLine.layoutParams = lastLineParams
                lastLine.setBackgroundColor(ContextCompat.getColor(topLine.context, lineColor))

                // 라인 Visible 컨트롤
                // 아이템 위치에 따라 Visible, Gone
                if(isShowLine && absoluteAdapterPosition == 0) {
                    topLine.visibility = View.VISIBLE
                } else {
                    topLine.visibility = View.GONE
                }
                if(isShowLine && absoluteAdapterPosition == itemCount - 1) {
                    lastLine.visibility = View.VISIBLE
                } else {
                    lastLine.visibility = View.GONE
                }
                bottomLine.visibility = View.GONE

                tvCollapsedTitle.apply {
                    text = data.title
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize.toFloat())
                    setTextColor(ContextCompat.getColor(context, titleTextColor))
                }
                tvContents.apply {
                    text = data.contents
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, contentsTextSize.toFloat())
                    setTextColor(ContextCompat.getColor(context, contentsTextColor))
                    setBackgroundColor(ContextCompat.getColor(context, contentsTextBackgroundColor))
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
                        // 접기
                        ToggleAnimation.toggleArrow(binding.ivCollapsedDirection, false)
                        toggleLayout(false, binding.rootLayout, binding.expandableView, absoluteAdapterPosition)
                        if(isShowLine) {
                            bottomLine.visibility = View.GONE
                            if(absoluteAdapterPosition == itemCount - 1) {
                                lastLine.visibility = View.VISIBLE
                            } else {
                                lastLine.visibility = View.GONE
                            }
                        }
                    } else {
                        // 펼치기
                        ToggleAnimation.toggleArrow(binding.ivCollapsedDirection, true)
                        toggleLayout(true, binding.rootLayout, binding.expandableView, absoluteAdapterPosition)
                        if(isShowLine) {
                            bottomLine.visibility = View.VISIBLE
                            lastLine.visibility = View.GONE
                        }
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

    override fun getLineHeight(): Float {
        return lineHeight
    }

    override fun getLineColor(): Int {
        return lineColor
    }

    override fun getShowLine(): Boolean {
        return isShowLine
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

    override fun setContentsTextBackgroundColor(color: Int) {
        contentsTextBackgroundColor = color
    }

    override fun setIconSize(widthDp: Int?, heightDp: Int?) {
        widthDp?.let {
            if(it > maxIconSize) {
                iconWidth = maxIconSize
            } else if(it <= 0) {
                iconWidth = null
            } else {
                iconWidth = widthDp
            }
        }
        heightDp?.let {
            if(it > maxIconSize) {
                iconHeight = maxIconSize
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

    override fun setShowLine(isShow: Boolean) {
        isShowLine = isShow
    }

    override fun setLineColor(color: Int) {
        lineColor = color
    }

    override fun setLineHeight(heightDp: Float) {
        lineHeight = heightDp
    }
}