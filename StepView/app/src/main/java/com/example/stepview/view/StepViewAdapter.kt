package com.example.stepview.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stepview.R
import com.example.stepview.databinding.ItemStepEndBinding
import com.example.stepview.databinding.ItemStepMiddleBinding
import com.example.stepview.databinding.ItemStepStartBinding

class StepViewAdapter : ListAdapter<StepViewModel, RecyclerView.ViewHolder>(diffUtil), BaseStepView {

    private var currentStep = 1
    private var list = ArrayList<StepViewModel>()
    private var isOpenTitle = false
    private var isOpenSubTitle = false

    // 디폴트 이이콘
    // completed: 완료된 상태, selected: 현재 선택된 상태, before: 완료되지 않은 상태
    private val beforeImgDefault = R.drawable.ic_check_box_black_24
    private var beforeImg: Int? = null
    private val selectedImgDefault = R.drawable.ic_check_box_active_24
    private var selectedImg: Int? = null
    private val completedImgDefault = R.drawable.car_body_default_black_24
    private var completedImg: Int? = null

    // 디폴트 중간 선 색
    private val beforeLineColorDefault = R.color.black
    private var beforeLineColor: Int? = null
    private val completedLineColorDefault = R.color.color_27afe9
    private var completedLineColor: Int? = null

    // 디폴트 내용 텍스트 색
    private val beforeContentsTextColorDefault = R.color.black
    private var beforeContentsTextColor: Int? = null
    private val selectedContentsTextColorDefault = R.color.permission_point
    private var selectedContentsTextColor: Int? = null
    private val completedContentsTextColorDefault = R.color.color_27afe9
    private var completedContentsTextColor: Int? = null

    // 단계 클릭 가능 여부
    private var isStepClickable = false

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            0 -> {
                StartStepViewHolder(ItemStepStartBinding.inflate(LayoutInflater.from(parent.context), parent,false))
            }
            itemCount - 1 -> {
                EndStepViewHolder(ItemStepEndBinding.inflate(LayoutInflater.from(parent.context), parent,false))
            }
            else -> {
                MiddleStepViewHolder(ItemStepMiddleBinding.inflate(LayoutInflater.from(parent.context), parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0) {
            (holder as StartStepViewHolder).bind(getItem(position))
        } else if(position == itemCount - 1) {
            (holder as EndStepViewHolder).bind(getItem(position))
        } else {
            (holder as MiddleStepViewHolder).bind(getItem(position))
        }
    }

    inner class StartStepViewHolder(val binding: ItemStepStartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StepViewModel) {
            if(1 >= currentStep) {
                // 1 이하는 무조건 첫번째 단계라는 의미
                if(beforeImg == null) {
                    beforeImgDefault
                } else {
                    beforeImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(beforeImgDefault)
                        .into(binding.ivStep)
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(beforeContentsTextColor == null) {
                        beforeContentsTextColorDefault
                    } else {
                        beforeContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            } else if(2 == currentStep) {
                // 현재 단계가 2이면 1번째 단계는 끝났다는 의미이므로 완료 표시
                // 그러나 아직 2단계이므로 endLineView 색을 유지

                if(completedImg == null) {
                    completedImgDefault
                } else {
                    completedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(completedImgDefault)
                        .into(binding.ivStep)
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(completedContentsTextColor == null) {
                        completedContentsTextColorDefault
                    } else {
                        completedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            } else {
                // 현재 1이나 2단계가 아니라면 무조건 완료 표시
                if(completedImg == null) {
                    completedImgDefault
                } else {
                    completedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(completedImgDefault)
                        .into(binding.ivStep)
                }

                if(completedLineColor == null) {
                    completedLineColorDefault
                } else {
                    completedLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(completedContentsTextColor == null) {
                        completedContentsTextColorDefault
                    } else {
                        completedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }

            // 메인 타이틀 Show/Hide
            if(isOpenTitle) {
                binding.tvTitle.visibility = View.VISIBLE
            } else {
                binding.tvTitle.visibility = View.GONE
            }

            // 서브 타이틀 Show/Hide
            if(isOpenSubTitle) {

            } else {

            }

            // 현재 선택된 단계
            if(currentStep-1 == absoluteAdapterPosition) {
                if(selectedImg == null) {
                    selectedImgDefault
                } else {
                    selectedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(selectedImgDefault)
                        .into(binding.ivStep)
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(selectedContentsTextColor == null) {
                        selectedContentsTextColorDefault
                    } else {
                        selectedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }

            binding.root.setOnClickListener {
                if(isStepClickable) {
                    setCurrentStep(absoluteAdapterPosition + 1)
                    onItemClickListener?.let {
                        it(getCurrentStep(), data)
                    }
                }
            }
        }
    }

    inner class MiddleStepViewHolder(val binding: ItemStepMiddleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StepViewModel) {
            if((currentStep-2) == absoluteAdapterPosition) {
                // 현재 선택된 단계의 바로 전 단계를 의미
                // startLineView와 endLineView를 다르게 해주어야 한다.
                if(completedImg == null) {
                    completedImgDefault
                } else {
                    completedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(completedImgDefault)
                        .into(binding.ivStep)
                }

                if(completedLineColor == null) {
                    completedLineColorDefault
                } else {
                    completedLineColor
                }?.let {
                    binding.startLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(completedContentsTextColor == null) {
                        completedContentsTextColorDefault
                    } else {
                        completedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }
            else if((absoluteAdapterPosition+1) >= currentStep) {
                // 현재 선택된 단계의 다음 단계들을 의미한다. 그러므로 모두 미완료 표시
                if(beforeImg == null) {
                    beforeImgDefault
                } else {
                    beforeImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(beforeImgDefault)
                        .into(binding.ivStep)
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.startLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(beforeContentsTextColor == null) {
                        beforeContentsTextColorDefault
                    } else {
                        beforeContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            } else {
                // 현재 선택된 단계의 바로 전 단계도 아니고 현재 선택된 단계의 다음 단계들도 아니므로 모두 완료 표시
                if(completedImg == null) {
                    completedImgDefault
                } else {
                    completedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(completedImgDefault)
                        .into(binding.ivStep)
                }

                if(completedLineColor == null) {
                    completedLineColorDefault
                } else {
                    completedLineColor
                }?.let {
                    binding.startLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                if(completedLineColor == null) {
                    completedLineColorDefault
                } else {
                    completedLineColor
                }?.let {
                    binding.endLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(completedContentsTextColor == null) {
                        completedContentsTextColorDefault
                    } else {
                        completedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }

            // 메인 타이틀 Show/Hide
            if(isOpenTitle) {
                binding.tvTitle.visibility = View.VISIBLE
            } else {
                binding.tvTitle.visibility = View.GONE
            }

            // 서브 타이틀 Show/Hide
            if(isOpenSubTitle) {

            } else {

            }

            // 현재 선택된 단계
            if(currentStep-1 == absoluteAdapterPosition) {
                if(selectedImg == null) {
                    selectedImgDefault
                } else {
                    selectedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(selectedImgDefault)
                        .into(binding.ivStep)
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(selectedContentsTextColor == null) {
                        selectedContentsTextColorDefault
                    } else {
                        selectedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }

            binding.root.setOnClickListener {
                if(isStepClickable) {
                    setCurrentStep(absoluteAdapterPosition + 1)
                    onItemClickListener?.let {
                        it(getCurrentStep(), data)
                    }
                }
            }
        }
    }

    inner class EndStepViewHolder(val binding: ItemStepEndBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StepViewModel) {
            if(currentStep <= itemCount) {
                // 마지막 단계의 전 단계들을 의미
                if(beforeImg == null) {
                    beforeImgDefault
                } else {
                    beforeImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(beforeImgDefault)
                        .into(binding.ivStep)
                }

                if(beforeLineColor == null) {
                    beforeLineColorDefault
                } else {
                    beforeLineColor
                }?.let {
                    binding.startLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(beforeContentsTextColor == null) {
                        beforeContentsTextColorDefault
                    } else {
                        beforeContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            } else if(currentStep > itemCount) {
                // 단계(currentStep)는 1단계부터 시작하므로 마지막 단계를 완료 했을 때 체크
                if(completedImg == null) {
                    completedImgDefault
                } else {
                    completedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(completedImgDefault)
                        .into(binding.ivStep)
                }

                if(completedLineColor == null) {
                    completedLineColorDefault
                } else {
                    completedLineColor
                }?.let {
                    binding.startLineView.setBackgroundColor(ContextCompat.getColor(binding.root.context, it))
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(completedContentsTextColor == null) {
                        completedContentsTextColorDefault
                    } else {
                        completedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }
//            else {
//                Glide.with(binding.root.context)
//                    .load(R.drawable.check_black_off)
//                    .into(binding.ivStep)
//                binding.startLineView.setBackgroundColor(Color.parseColor("#000000"))
//                binding.tvTitle.apply {
//                    text = data.title
//                    setTextColor(Color.parseColor("#000000"))
//                }
//            }

            // 메인 타이틀 Show/Hide
            if(isOpenTitle) {
                binding.tvTitle.visibility = View.VISIBLE
            } else {
                binding.tvTitle.visibility = View.GONE
            }

            // 서브 타이틀 Show/Hide
            if(isOpenSubTitle) {

            } else {

            }

            // 현재 선택된 단계
            if(currentStep-1 == absoluteAdapterPosition) {
                if(selectedImg == null) {
                    selectedImgDefault
                } else {
                    selectedImg
                }?.let {
                    Glide.with(binding.root.context)
                        .load(it)
                        .error(selectedImgDefault)
                        .into(binding.ivStep)
                }

                binding.tvTitle.apply {
                    text = data.title
                    if(selectedContentsTextColor == null) {
                        selectedContentsTextColorDefault
                    } else {
                        selectedContentsTextColor
                    }?.let {
                        setTextColor(ContextCompat.getColor(binding.root.context, it))
                    }
                }
            }

            binding.root.setOnClickListener {
                if(isStepClickable) {
                    setCurrentStep(absoluteAdapterPosition + 1)
                    onItemClickListener?.let {
                        it(getCurrentStep(), data)
                    }
                }
            }
        }
    }

    override fun setList(list: List<StepViewModel>) {
        this.list = list as ArrayList
    }

    override fun setCurrentStep(step: Int) {
        currentStep = step
        notifyDataSetChanged()
    }

    override fun getCurrentStep(): Int = currentStep

    override fun openTitle() {
        isOpenTitle = true
        notifyDataSetChanged()
    }

    override fun closeTitle() {
        isOpenTitle = false
        notifyDataSetChanged()
    }

    override fun openSubTitle() {
        isOpenSubTitle = true
        notifyDataSetChanged()
    }

    override fun closeSubTitle() {
        isOpenSubTitle = false
        notifyDataSetChanged()
    }

    override fun setCompletedImg(img: Int) {
        completedImg = img
    }

    override fun setSelectedImg(img: Int) {
        selectedImg = img
    }

    override fun setBeforeImg(img: Int) {
        beforeImg = img
    }

    override fun setCompletedLineColor(color: Int) {
        completedLineColor = color
    }

    override fun setBeforeLineColor(color: Int) {
        beforeLineColor = color
    }

    override fun setCompletedContentsTextColor(color: Int) {
        completedContentsTextColor = color
    }

    override fun setSelectedContentsTextColor(color: Int) {
        selectedContentsTextColor = color
    }

    override fun setBeforeContentsTextColor(color: Int) {
        beforeContentsTextColor = color
    }

    override fun isStepClickable(clickable: Boolean) {
        isStepClickable = clickable
    }

    private var onItemClickListener : ((Int, StepViewModel) -> String) ?= null

    fun setOnItemClickListener(listener : (Int, StepViewModel) -> String) {
        onItemClickListener = listener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<StepViewModel>() {
            override fun areContentsTheSame(oldItem: StepViewModel, newItem: StepViewModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: StepViewModel, newItem: StepViewModel) =
                oldItem.idx == newItem.idx
        }
    }

}