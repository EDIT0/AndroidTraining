package com.example.menuoptionselectionviewdemo1.option.optionmenu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.R
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionOptionalOneDetailBinding
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionWithCheckBoxClickListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionWithRadioButtonClickListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

class OptionalOneMenuAdapter: ListAdapter<MenuOptionModel.OptionModel.OptionMenuModel, RecyclerView.ViewHolder>(diffUtil) {


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomViewHolder(ItemMenuOptionOptionalOneDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CustomViewHolder) {
            holder.bind(getItem(position))
        }
    }

    inner class CustomViewHolder(private val binding: ItemMenuOptionOptionalOneDetailBinding) : RecyclerView.ViewHolder(binding.root) {
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
                binding.cbButton.isEnabled = false
                binding.optionMenuLayout.isEnabled = false
                binding.tvAdditionalPrice.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuRemainAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
            }

            // 만약 품절 처리를 안하고 남은 갯수가 0개라면..
            if(optionMenuModel.remainAmount == 0) {
                binding.cbButton.isEnabled = false
                binding.optionMenuLayout.isEnabled = false
                binding.tvAdditionalPrice.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuRemainAmount.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                binding.tvOptionMenuTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
            }

            // 현재 메뉴 옵션들에 대한 라디오 버튼을 저장하여 관리한다.
            onMenuOptionWithCheckBoxClickListener?.saveCheckBox(binding.cbButton)

            binding.cbButton.setOnClickListener {
                onMenuOptionWithCheckBoxClickListener?.onMenuOptionWithCheckBoxClick(binding.cbButton, adapterPosition, optionMenuModel)
            }

            binding.optionMenuLayout.setOnClickListener {
                binding.cbButton.performClick()
            }
        }
    }

    private var onMenuOptionWithCheckBoxClickListener: OnMenuOptionWithCheckBoxClickListener? = null
    fun setOnMenuOptionClickListener(onMenuOptionWithCheckBoxClickListener: OnMenuOptionWithCheckBoxClickListener) {
        this.onMenuOptionWithCheckBoxClickListener = onMenuOptionWithCheckBoxClickListener
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