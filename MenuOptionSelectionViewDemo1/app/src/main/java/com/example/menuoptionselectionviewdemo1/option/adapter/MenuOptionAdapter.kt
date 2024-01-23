package com.example.menuoptionselectionviewdemo1.option.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionMandatoryOneBinding
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionMandatoryTwoBinding
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionOptionalOneBinding
import com.example.menuoptionselectionviewdemo1.option.viewholder.MandatoryOneViewHolder
import com.example.menuoptionselectionviewdemo1.option.viewholder.MandatoryTwoViewHolder
import com.example.menuoptionselectionviewdemo1.option.callback.OnOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnOptionUpdateWithPositionListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel
import com.example.menuoptionselectionviewdemo1.option.viewholder.OptionalOneViewHolder

class MenuOptionAdapter : ListAdapter<MenuOptionModel.OptionModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
//        val selectionViewType: Int = item.selectionViewType
        val isMandatorySelection: Boolean = item.isMandatorySelection
        val amount: Int = item.amount
        val isMaximumAmount: Boolean = item.isMaximumAmount

        // 어떤 View를 사용 할 것인지 설정 (
        if(isMandatorySelection && amount == 1) {
            // 필수 ( o 메뉴옵션 )
            return VIEW_TYPE_MANDATORY_ONE
        } else if(isMandatorySelection && amount > 1) {
            // 필수 ( 메뉴옵션 - 0 + )
            return VIEW_TYPE_MANDATORY_TWO
        } else if(!isMandatorySelection) {
            // 선택 ( ㅁ 메뉴옵션 )
            return VIEW_TYPE_OPTIONAL_ONE
        }
        return VIEW_TYPE_ERROR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_MANDATORY_ONE -> {
                MandatoryOneViewHolder(ItemMenuOptionMandatoryOneBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            VIEW_TYPE_MANDATORY_TWO -> {
                MandatoryTwoViewHolder(ItemMenuOptionMandatoryTwoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            VIEW_TYPE_OPTIONAL_ONE -> {
                OptionalOneViewHolder(ItemMenuOptionOptionalOneBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
//            VIEW_TYPE_ERROR -> {
//
//            }
            else -> {
                // TODO 고쳐야함
                MandatoryOneViewHolder(ItemMenuOptionMandatoryOneBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MandatoryOneViewHolder) {
            holder.bind(getItem(position))
            val i = position
            holder.setOnOptionUpdateListener(object : OnOptionUpdateListener {
                override fun onOptionUpdate(optionModel: MenuOptionModel.OptionModel) {
                    onOptionUpdateWithPositionListener.onOptionUpdateWithPosition(
                        optionModel = optionModel,
                        position = i
                    )
                }
            })
        } else if(holder is MandatoryTwoViewHolder) {
            holder.bind(getItem(position))
            val i = position
            holder.setOnOptionUpdateListener(object : OnOptionUpdateListener {
                override fun onOptionUpdate(optionModel: MenuOptionModel.OptionModel) {
                    onOptionUpdateWithPositionListener.onOptionUpdateWithPosition(
                        optionModel = optionModel,
                        position = i
                    )
                }
            })
        } else if(holder is OptionalOneViewHolder) {
            holder.bind(getItem(position))
            val i = position
            holder.setOnOptionUpdateListener(object : OnOptionUpdateListener {
                override fun onOptionUpdate(optionModel: MenuOptionModel.OptionModel) {
                    onOptionUpdateWithPositionListener.onOptionUpdateWithPosition(
                        optionModel = optionModel,
                        position = i
                    )
                }
            })
        }
    }

    private lateinit var onOptionUpdateWithPositionListener: OnOptionUpdateWithPositionListener
    fun setOnOptionWithPositionListener(onOptionUpdateWithPositionListener: OnOptionUpdateWithPositionListener) {
        this.onOptionUpdateWithPositionListener = onOptionUpdateWithPositionListener
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MenuOptionModel.OptionModel>() {
            override fun areContentsTheSame(oldItem: MenuOptionModel.OptionModel, newItem: MenuOptionModel.OptionModel) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MenuOptionModel.OptionModel, newItem: MenuOptionModel.OptionModel) =
                oldItem.optionTitle == newItem.optionTitle
        }

        const val VIEW_TYPE_ERROR = 401

        const val VIEW_TYPE_MANDATORY_ONE = 101
        const val VIEW_TYPE_MANDATORY_TWO = 102

        const val VIEW_TYPE_OPTIONAL_ONE = 201


    }
}