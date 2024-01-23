package com.example.menuoptionselectionviewdemo1.option.viewholder

import android.util.Log
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.Utils
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionMandatoryOneBinding
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionWithRadioButtonClickListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.optionmenu.adapter.MandatoryOneMenuAdapter

class MandatoryOneViewHolder(
    private val binding: ItemMenuOptionMandatoryOneBinding
): RecyclerView.ViewHolder(binding.root) {

    private val mandatoryOneMenuAdapter = MandatoryOneMenuAdapter()
    private val radioButtonList = ArrayList<RadioButton>() // 이 메뉴 선택에 대한 옵션(라디오 버튼)이 모두 들어가 있다.
    private var currentCheckCount = 0 // 현재 체크된 라디오 버튼 갯수
    private val selectedOptionList: ArrayList<MenuOptionModel.OptionModel.OptionMenuModel> = ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>() // 현재 선택된 옵션(라디오 버튼)에 대한 정보를 가지고 있다.
    private var currentOptionList: ArrayList<MenuOptionModel.OptionModel.OptionMenuModel> = ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>()

    init {
        binding.rvOptionMenu.apply {
            adapter = mandatoryOneMenuAdapter
            layoutManager = object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return true
                }

                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
        }
    }

    fun bind(optionModel: MenuOptionModel.OptionModel) {
        binding.apply {
            tvTitle.text = optionModel.optionTitle
            tvAmount.text = if(optionModel.isMaximumAmount && optionModel.isMinimumAmount) { // 최대, 최소로 고를 수 있는 수가 정해져 있나요?
                "${optionModel.minimumAmount}~${optionModel.maximumAmount}개"
            } else if(optionModel.isMaximumAmount) { // 최대 고를 수 있는 수가 정해져 있나요?
                "최대 ${optionModel.maximumAmount}개"
            } else if(optionModel.isMinimumAmount) { // 최소 고를 수 있는 수가 정해져 있나요?
                "최소 ${optionModel.minimumAmount}개"
            } else { // 최대, 최소 값이 아닌가요?
                optionModel.amount.toString() + "개"
            }

            currentOptionList = optionModel.menuList as ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>

            mandatoryOneMenuAdapter.setOnMenuOptionClickListener(object : OnMenuOptionWithRadioButtonClickListener {
                override fun onMenuOptionWithRadioButtonClick(radioButton: RadioButton, position: Int, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
                    if(optionModel.isMaximumAmount) { // 최대값이 있음
                        if(checkCurrentChecked() < optionModel.maximumAmount) { // 최대 고를 수 있는 갯수보다 작나요?
                            if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
                                off(position, optionModel, optionMenuModel)
                            } else { // 버튼이 눌려있지 않나요?
                                on(position, optionModel, optionMenuModel)
                            }
                        } else if(checkCurrentChecked() == optionModel.maximumAmount) { // 최대 고를 수 있는 갯수에 도달했나요?
                            if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
                                off(position, optionModel, optionMenuModel)
                            } else { // 버튼이 눌려있지 않나요?
                                Utils.showToast(root.context, "${optionModel.maximumAmount}개까지 선택 가능합니다.")
                            }
                        }
                    } else { // 최대값이 없음
                        if(checkCurrentChecked() < optionModel.amount) { // 최대 고를 수 있는 갯수보다 작나요?
                            if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
                                off(position, optionModel, optionMenuModel)
                            } else { // 버튼이 눌려있지 않나요?
                                on(position, optionModel, optionMenuModel)
                            }
                        } else if(checkCurrentChecked() == optionModel.amount) { // 최대 고를 수 있는 갯수에 도달했나요?
                            if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
                                off(position, optionModel, optionMenuModel)
                            } else { // 버튼이 눌려있지 않나요?
                                Utils.showToast(root.context, "${optionModel.amount}개까지 선택 가능합니다.")
                            }
                        }
                    }

//                    if(checkCurrentChecked() < optionModel.amount ) { // 최대 고를 수 있는 갯수보다 작나요?
//                        if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
//                            off(position, optionModel, optionMenuModel)
//                        } else { // 버튼이 눌려있지 않나요?
//                            on(position, optionModel, optionMenuModel)
//                        }
//                    } else if(checkCurrentChecked() == optionModel.amount) { // 최대 고를 수 있는 갯수에 도달했나요?
//                        if(isRadioButtonChecked(position)) { // 버튼이 눌려있나요?
//                            off(position, optionModel, optionMenuModel)
//                        } else { // 버튼이 눌려있지 않나요?
//                            Utils.showToast(root.context, "${optionModel.amount}개까지 선택 가능합니다.")
//                        }
//                    }
                }

                override fun saveRadioButton(radioButton: RadioButton) {
                    radioButtonList.add(radioButton)
                }
            })

            mandatoryOneMenuAdapter.submitList(currentOptionList)
        }
    }

    // 라디오 버튼 상태 체크
    fun isRadioButtonChecked(position: Int): Boolean {
        return radioButtonList[position].isChecked
    }

    // 현재 체크된 라디오 버튼이 몇개인지 반환
    fun checkCurrentChecked(): Int {
        currentCheckCount = 0
        for(i in 0 until radioButtonList.size) {
            if(radioButtonList[i].isChecked) {
                currentCheckCount++
            }
        }
        return currentCheckCount
    }

    // 현재 선택한 위치 체크하고 나머지 모두 해제
    fun clearRadioButtonExceptFor(position: Int) {
        for(i in 0 until radioButtonList.size) {
            if(i != position) {
                radioButtonList[i].isChecked = false
            } else {
                radioButtonList[i].isChecked = true
            }
        }
    }

    // 라디오 버튼 on
    fun on(position: Int, optionModel: MenuOptionModel.OptionModel, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
        radioButtonList[position].isChecked = true
        selectedOptionList.add(optionMenuModel)
        currentOptionList[position].selectedCount = ++optionMenuModel.selectedCount
        mandatoryOneMenuAdapter.submitList(currentOptionList)
        optionModel.menuList = currentOptionList
        onOptionUpdateListener.onOptionUpdate(
//            MenuOptionModel.OptionModel(
//                isMandatorySelection = optionModel.isMandatorySelection,
//                optionTitle = optionModel.optionTitle,
//                amount = optionModel.amount,
//                isMaximumAmount = optionModel.isMaximumAmount,
//                menuList = currentOptionList
//            )
            optionModel
        )
//        for(i in 0 until currentOptionList.size) {
//            Log.d("MYTAG", "on ${currentOptionList[i].optionMenuTitle} ${currentOptionList[i].selectedCount}")
//        }
    }

    // 라디오 버튼 off
    fun off(position: Int, optionModel: MenuOptionModel.OptionModel, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
        radioButtonList[position].isChecked = false
        selectedOptionList.remove(optionMenuModel)
        currentOptionList[position].selectedCount = --optionMenuModel.selectedCount
        mandatoryOneMenuAdapter.submitList(currentOptionList)
        optionModel.menuList = currentOptionList
        onOptionUpdateListener.onOptionUpdate(
//            MenuOptionModel.OptionModel(
//                isMandatorySelection = optionModel.isMandatorySelection,
//                optionTitle = optionModel.optionTitle,
//                amount = optionModel.amount,
//                isMaximumAmount = optionModel.isMaximumAmount,
//                menuList = currentOptionList
//            )
            optionModel
        )
//        for(i in 0 until currentOptionList.size) {
//            Log.d("MYTAG", "off ${currentOptionList[i].optionMenuTitle} ${currentOptionList[i].selectedCount}")
//        }
    }

    private lateinit var onOptionUpdateListener: OnOptionUpdateListener
    fun setOnOptionUpdateListener(onOptionUpdateListener: OnOptionUpdateListener) {
        this.onOptionUpdateListener = onOptionUpdateListener
    }
}