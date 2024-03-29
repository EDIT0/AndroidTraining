package com.example.accordioninfoviewrv.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accordioninfoviewrv.R
import com.example.accordioninfoviewrv.databinding.AccordionTextInfoRecyclerviewBinding

class AccordionTextInfoRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), BaseAccordionTextInfoView.AccordionTextInfoContracts {

    private var binding: AccordionTextInfoRecyclerviewBinding = AccordionTextInfoRecyclerviewBinding.inflate(LayoutInflater.from(context), this, true)

    private var accordionTextInfoAdapter: AccordionTextInfoAdapter = AccordionTextInfoAdapter()

    override fun setTitleTextSize(sizeDp: Int) {
        accordionTextInfoAdapter.setTitleTextSize(sizeDp)
    }

    override fun setContentsTextSize(sizeDp: Int) {
        accordionTextInfoAdapter.setContentsTextSize(sizeDp)
    }

    override fun setTitleTextColor(color: Int) {
        accordionTextInfoAdapter.setTitleTextColor(color)
    }

    override fun setContentsTextColor(color: Int) {
        accordionTextInfoAdapter.setContentsTextColor(color)
    }

    override fun setContentsTextBackgroundColor(color: Int) {
        accordionTextInfoAdapter.setContentsTextBackgroundColor(color)
    }

    override fun setIconSize(widthDp: Int?, height: Int?) {
        accordionTextInfoAdapter.setIconSize(widthDp, height)
    }

    override fun setIcon(imgDrawable: Int) {
        accordionTextInfoAdapter.setIcon(imgDrawable)
    }

    override fun setShowLine(isShow: Boolean) {
        accordionTextInfoAdapter.setShowLine(isShow)
    }

    override fun setLineColor(color: Int) {
        accordionTextInfoAdapter.setLineColor(color)
    }

    override fun setLineHeight(heightDp: Float) {
        accordionTextInfoAdapter.setLineHeight(heightDp)
    }

    override fun initializeAndSetList(list: List<AccordionTextInfoModel>) {
        binding.rv.apply {
            adapter = accordionTextInfoAdapter
            if(accordionTextInfoAdapter.getShowLine()) {
                addItemDecoration(FoldTextInfoItemDecoration(Util.dpToPx(context,20f).toFloat(), Util.dpToPx(context, accordionTextInfoAdapter.getLineHeight()).toFloat(), ContextCompat.getColor(context, accordionTextInfoAdapter.getLineColor())))
            }
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }

                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
        }

        accordionTextInfoAdapter.submitList(list)
    }

}