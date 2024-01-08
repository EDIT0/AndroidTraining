package com.example.stepview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stepview.databinding.StepViewBinding

class StepView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), BaseStepView.StepViewContracts {

    private var binding: StepViewBinding = StepViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var stepViewAdapter: StepViewAdapter? = null

    private var stepList = ArrayList<StepViewModel>()

    override fun initStepView() {
        stepViewAdapter = null
        stepViewAdapter = StepViewAdapter()
    }

    override fun setList(list: List<StepViewModel>) {
        stepList = list as ArrayList<StepViewModel>
        stepViewAdapter?.setList(stepList)
    }

    override fun setCurrentStep(step: Int) {
        if((stepList.size + 1) < step || step <= 0) {
            return
        }
        stepViewAdapter?.setCurrentStep(step)
    }

    override fun getCurrentStep(): Int = stepViewAdapter?.getCurrentStep()?:-1

    override fun openTitle() {
        stepViewAdapter?.openTitle()
    }

    override fun closeTitle() {
        stepViewAdapter?.closeTitle()
    }

    override fun openSubTitle() {
        stepViewAdapter?.openSubTitle()
    }

    override fun closeSubTitle() {
        stepViewAdapter?.closeSubTitle()
    }

    override fun getStepListSize(): Int = stepList.size
    override fun setCompletedImg(img: Int) {
        stepViewAdapter?.setCompletedImg(img)
    }

    override fun setSelectedImg(img: Int) {
        stepViewAdapter?.setSelectedImg(img)
    }

    override fun setBeforeImg(img: Int) {
        stepViewAdapter?.setBeforeImg(img)
    }

    override fun setCompletedLineColor(color: Int) {
        stepViewAdapter?.setCompletedLineColor(color)
    }

    override fun setBeforeLineColor(color: Int) {
        stepViewAdapter?.setBeforeLineColor(color)
    }

    override fun setCompletedContentsTextColor(color: Int) {
        stepViewAdapter?.setCompletedContentsTextColor(color)
    }

    override fun setSelectedContentsTextColor(color: Int) {
        stepViewAdapter?.setSelectedContentsTextColor(color)
    }

    override fun setBeforeContentsTextColor(color: Int) {
        stepViewAdapter?.setBeforeContentsTextColor(color)
    }

    override fun isStepClickable(clickable: Boolean) {
        stepViewAdapter?.isStepClickable(clickable)
    }

    override fun show() {
        if(stepList.size != 0) {
            binding.rvStep.apply {
                adapter = stepViewAdapter
                layoutManager = (object : LinearLayoutManager(binding.root.context) {
                    override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                        lp.width = width / stepList.size
                        return true
                    }

                    override fun setOrientation(orientation: Int) {
                        super.setOrientation(LinearLayoutManager.HORIZONTAL)
                    }

                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }

                    override fun canScrollVertically(): Boolean {
                        return false
                    }
                })
            }
            stepViewAdapter?.submitList(stepList.toMutableList())
        }
    }
}