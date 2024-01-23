package com.example.menuoptionselectionviewdemo1.menuview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.NestedScrollView
import com.example.menuoptionselectionviewdemo1.Utils
import com.example.menuoptionselectionviewdemo1.databinding.MenuViewBinding
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

class MenuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr){

    private var binding: MenuViewBinding = MenuViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var currentMenuOptionModel: MenuOptionModel? = null
    private var currentMenuCount = 0

    fun setMenu(menuOptionModel: MenuOptionModel) {
        currentMenuOptionModel = menuOptionModel

        binding.itemMenuTitle.tvMenuTitle.text = menuOptionModel.menuTitle
        binding.itemMenuTitle.tvMenuSubTitle.text = menuOptionModel.menuSubTitle
        binding.menuPriceLayout.tvPrice.text = Utils.formatCurrency(menuOptionModel.menuPrice.toLong()) + "원"
        binding.rvMenuOption.apply {
//            setMenuOption(menuOptionModel.menuPrice, menuOptionModel.menuCount, menuOptionModel.thumbnail, menuOptionModel.optionList)
            setMenuOption(menuOptionModel)
            setOnMenuOptionUpdateListener(object : OnMenuOptionUpdateListener {
                override fun onMenuOptionUpdate(menuOptionModel: MenuOptionModel) {
                    currentMenuOptionModel = menuOptionModel
                    currentMenuOptionModel!!.menuCount = currentMenuCount
                    onMenuOptionUpdateListener.onMenuOptionUpdate(currentMenuOptionModel!!)
                }
            })
        }
        currentMenuCount = menuOptionModel.menuCount
        binding.menuAmountControl.tvMenuCount.text = "${currentMenuCount}개"
        binding.menuAmountControl.tvMinus.setOnClickListener {
            if(currentMenuCount <= 1) {
                return@setOnClickListener
            }
            currentMenuOptionModel?.let {
                currentMenuCount--
                binding.menuAmountControl.tvMenuCount.text = "${currentMenuCount}개"
                currentMenuOptionModel!!.menuCount = currentMenuCount
                onMenuOptionUpdateListener.onMenuOptionUpdate(currentMenuOptionModel!!)
            }
        }
        binding.menuAmountControl.tvPlus.setOnClickListener {
            if(currentMenuCount >= 10) {
                return@setOnClickListener
            }
            currentMenuOptionModel?.let {
                currentMenuCount++
                binding.menuAmountControl.tvMenuCount.text = "${currentMenuCount}개"
                currentMenuOptionModel!!.menuCount = currentMenuCount
                onMenuOptionUpdateListener.onMenuOptionUpdate(currentMenuOptionModel!!)
            }
        }
    }

    private lateinit var onMenuOptionUpdateListener: OnMenuOptionUpdateListener
    fun setOnMenuOptionUpdateListener(onMenuOptionUpdateListener: OnMenuOptionUpdateListener) {
        this.onMenuOptionUpdateListener = onMenuOptionUpdateListener
    }

}