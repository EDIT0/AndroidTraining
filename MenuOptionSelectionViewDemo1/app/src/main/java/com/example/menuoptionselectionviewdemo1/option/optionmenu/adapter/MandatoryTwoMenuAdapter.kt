package com.example.menuoptionselectionviewdemo1.option.optionmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.R
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionMandatoryTwoDetailBinding
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionWithNumberPickerClickListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

class MandatoryTwoMenuAdapter: ListAdapter<MenuOptionModel.OptionModel.OptionMenuModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomViewHolder(ItemMenuOptionMandatoryTwoDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CustomViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class CustomViewHolder(private val binding: ItemMenuOptionMandatoryTwoDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
            // 옵션 메뉴 이름 설정
            binding.tvOptionMenuTitle.text = optionMenuModel.optionMenuTitle
            // 추가 가격 설정
            optionMenuModel.additionalPrice?.let {
                if(it > 0) {
                    binding.tvAdditionalPrice.text = " (+${it}원)"
                } else {
                    binding.tvAdditionalPrice.text = " (${it}원)"
                }
            }
            // 남은 수에 대한 설정
            if(optionMenuModel.remainAmount == null || optionMenuModel.remainAmount == 0) {
                binding.tvOptionMenuRemainAmount.visibility = View.GONE
            } else {
                binding.tvOptionMenuRemainAmount.text = "(${optionMenuModel.remainAmount}개 남음)"
            }
            // 품절 관련 설정
            if(optionMenuModel.isSoldOut) {
                binding.optionMenuCountLayout.isEnabled = false
                binding.optionMenuLayout.isEnabled = false
                binding.plusLayout.setBackgroundResource(R.drawable.background_oval_stroke_gray_2)
                binding.minusLayout.setBackgroundResource(R.drawable.background_oval_stroke_gray_2)
                binding.tvPlus.isEnabled = false
                binding.tvMinus.isEnabled = false
                binding.tvPlus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvMinus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuCount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvAdditionalPrice.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuRemainAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
            } else {
                binding.optionMenuCountLayout.isEnabled = true
                binding.optionMenuLayout.isEnabled = true
                binding.plusLayout.setBackgroundResource(R.drawable.background_oval_stroke_blue_2)
                binding.minusLayout.setBackgroundResource(R.drawable.background_oval_stroke_blue_2)
                binding.tvPlus.isEnabled = true
                binding.tvMinus.isEnabled = true
                binding.tvPlus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue))
                binding.tvMinus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue))
                binding.tvOptionMenuCount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue))
                binding.tvAdditionalPrice.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.tvOptionMenuRemainAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                binding.tvOptionMenuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            }

            // 만약 품절 처리를 안하고 남은 갯수가 0개라면..
            if(optionMenuModel.remainAmount == 0) {
                binding.optionMenuCountLayout.isEnabled = false
                binding.optionMenuLayout.isEnabled = false
                binding.plusLayout.setBackgroundResource(R.drawable.background_oval_stroke_gray_2)
                binding.minusLayout.setBackgroundResource(R.drawable.background_oval_stroke_gray_2)
                binding.tvPlus.isEnabled = false
                binding.tvMinus.isEnabled = false
                binding.tvPlus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvMinus.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuCount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvAdditionalPrice.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuRemainAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
            }

            binding.tvMinus.apply {
                setOnClickListener {
                    onMenuOptionWithNumberPickerClickListener?.onMenuOptionWithNumberPickerClick(binding.tvOptionMenuCount, false, adapterPosition, optionMenuModel)
                }
            }

            binding.tvPlus.apply {
                setOnClickListener {
                    onMenuOptionWithNumberPickerClickListener?.onMenuOptionWithNumberPickerClick(binding.tvOptionMenuCount, true, adapterPosition, optionMenuModel)
                }
            }

            binding.tvOptionMenuCount.text = "${0}"

            // 현재 메뉴 옵션들에 대한 라디오 버튼을 저장하여 관리한다.
//            onMenuOptionClickListener?.saveRadioButton(binding.rbButton)
//
//            binding.rbButton.setOnClickListener {
//                onMenuOptionClickListener?.onMenuOptionClick(binding.rbButton, adapterPosition, optionMenuModel)
//            }
//
//            binding.optionMenuLayout.setOnClickListener {
//                binding.rbButton.performClick()
//            }
        }
    }

    private var onMenuOptionWithNumberPickerClickListener: OnMenuOptionWithNumberPickerClickListener? = null
    fun setOnMenuOptionClickListener(onMenuOptionWithWithNumberPickerClickListener: OnMenuOptionWithNumberPickerClickListener) {
        this.onMenuOptionWithNumberPickerClickListener = onMenuOptionWithWithNumberPickerClickListener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MenuOptionModel.OptionModel.OptionMenuModel>() {
            override fun areContentsTheSame(oldItem: MenuOptionModel.OptionModel.OptionMenuModel, newItem: MenuOptionModel.OptionModel.OptionMenuModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MenuOptionModel.OptionModel.OptionMenuModel, newItem: MenuOptionModel.OptionModel.OptionMenuModel) =
                oldItem.optionMenuTitle == newItem.optionMenuTitle
        }
    }

}