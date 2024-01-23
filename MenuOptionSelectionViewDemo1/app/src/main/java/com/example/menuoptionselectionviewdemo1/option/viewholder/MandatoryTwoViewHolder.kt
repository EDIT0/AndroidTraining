package com.example.menuoptionselectionviewdemo1.option.viewholder

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.Utils
import com.example.menuoptionselectionviewdemo1.databinding.ItemMenuOptionMandatoryTwoBinding
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionWithNumberPickerClickListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel
import com.example.menuoptionselectionviewdemo1.option.optionmenu.adapter.MandatoryTwoMenuAdapter

class MandatoryTwoViewHolder(
    private val binding: ItemMenuOptionMandatoryTwoBinding
): RecyclerView.ViewHolder(binding.root) {

    private val mandatoryTwoMenuAdapter = MandatoryTwoMenuAdapter()
//    private val radioButtonList = ArrayList<RadioButton>() // 이 메뉴 선택에 대한 옵션(라디오 버튼)이 모두 들어가 있다.
    private var currentCheckCount = 0 // 현재 체크된 라디오 버튼 갯수
//    private val selectedOptionList: ArrayList<MenuOptionModel.OptionModel.OptionMenuModel> = ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>() // 현재 선택된 옵션(라디오 버튼)에 대한 정보를 가지고 있다.
    private var currentOptionList: ArrayList<MenuOptionModel.OptionModel.OptionMenuModel> = ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>()

    init {
        binding.rvOptionMenu.apply {
            adapter = mandatoryTwoMenuAdapter
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
//            tvAmount.text = if(optionModel.isMaximumAmount) {
//                "최대 ${optionModel.amount}개"
//            } else {
//                optionModel.amount.toString() + "개"
//            }

            currentOptionList = optionModel.menuList as ArrayList<MenuOptionModel.OptionModel.OptionMenuModel>

            mandatoryTwoMenuAdapter.setOnMenuOptionClickListener(object : OnMenuOptionWithNumberPickerClickListener {
                override fun onMenuOptionWithNumberPickerClick(textView: TextView, isPlus: Boolean, position: Int, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
                    if(optionModel.isMaximumAmount) { // 최대값이 있음
                        if(checkCurrentAmount() < optionModel.maximumAmount) { // 최대 고를 수 있는 갯수보다 작나요?
                            if(isPlus) { // +버튼을 눌렀나요?
                                if(optionMenuModel.remainAmount != null) { // 남은 수량이 정해져 있나요?
                                    if(optionMenuModel.remainAmount <= optionMenuModel.selectedCount) { // 현재 선택된 수량이 남은 수량보다 같거나 큰가요?
                                        Utils.showToast(binding.root.context, "현재 옵션은 ${optionMenuModel.remainAmount}개까지 선택 가능합니다.")
                                    } else {
                                        plus(textView, position, optionModel, optionMenuModel)
                                    }
                                } else {
                                    plus(textView, position, optionModel, optionMenuModel)
                                }
                            } else { // -버튼을 눌렀나요?
                                if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?

                                } else { // 현재 수량이 0보다 큰가요?
                                    minus(textView, position, optionModel, optionMenuModel)
                                }
                            }
                        } else if(checkCurrentAmount() == optionModel.maximumAmount) { // 최대 고를 수 있는 갯수에 도달했나요?
                            if(isPlus) { // +버튼을 눌렀나요?
                                Utils.showToast(binding.root.context, "${optionModel.maximumAmount}개까지 선택 가능합니다.")
                            } else { // -버튼을 눌렀나요?
                                if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?

                                } else { // 현재 수량이 0보다 큰가요?
                                    minus(textView, position, optionModel, optionMenuModel)
                                }
                            }
                        }
                    } else { // 최대값이 없음
                        if(checkCurrentAmount() < optionModel.amount) { // 최대 고를 수 있는 갯수보다 작나요?
                            if(isPlus) { // +버튼을 눌렀나요?
                                if(optionMenuModel.remainAmount != null) { // 남은 수량이 정해져 있나요?
                                    if(optionMenuModel.remainAmount <= optionMenuModel.selectedCount) { // 현재 선택된 수량이 남은 수량보다 같거나 큰가요?
                                        Utils.showToast(binding.root.context, "현재 옵션은 ${optionMenuModel.remainAmount}개까지 선택 가능합니다.")
                                    } else {
                                        plus(textView, position, optionModel, optionMenuModel)
                                    }
                                } else {
                                    plus(textView, position, optionModel, optionMenuModel)
                                }
                            } else { // -버튼을 눌렀나요?
                                if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?

                                } else { // 현재 수량이 0보다 큰가요?
                                    minus(textView, position, optionModel, optionMenuModel)
                                }
                            }
                        } else if(checkCurrentAmount() == optionModel.amount) { // 최대 고를 수 있는 갯수에 도달했나요?
                            if(isPlus) { // +버튼을 눌렀나요?
                                Utils.showToast(binding.root.context, "${optionModel.amount}개까지 선택 가능합니다.")
                            } else { // -버튼을 눌렀나요?
                                if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?

                                } else { // 현재 수량이 0보다 큰가요?
                                    minus(textView, position, optionModel, optionMenuModel)
                                }
                            }
                        }
                    }

//                    if(checkCurrentAmount() < optionModel.amount) { // 최대 고를 수 있는 갯수보다 작나요?
//                        if(isPlus) { // +버튼을 눌렀나요?
//                            if(optionMenuModel.remainAmount != null) { // 남은 수량이 정해져 있나요?
//                                if(optionMenuModel.remainAmount <= optionMenuModel.selectedCount) { // 현재 선택된 수량이 남은 수량보다 같거나 큰가요?
//                                    Utils.showToast(binding.root.context, "현재 옵션은 ${optionMenuModel.remainAmount}개까지 선택 가능합니다.")
//                                } else {
//                                    plus(textView, position, optionModel, optionMenuModel)
//                                }
//                            } else {
//                                plus(textView, position, optionModel, optionMenuModel)
//                            }
//                        } else { // -버튼을 눌렀나요?
//                            if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?
//
//                            } else { // 현재 수량이 0보다 큰가요?
//                                minus(textView, position, optionModel, optionMenuModel)
//                            }
//                        }
//                    } else if(checkCurrentAmount() == optionModel.amount) { // 최대 고를 수 있는 갯수에 도달했나요?
//                        if(isPlus) { // +버튼을 눌렀나요?
//                            Utils.showToast(binding.root.context, "${optionModel.amount}개까지 선택 가능합니다.")
//                        } else { // -버튼을 눌렀나요?
//                            if(textView.text.toString().toInt() <= 0) { // 현재 수량이 0과 같거나 작은가요?
//
//                            } else { // 현재 수량이 0보다 큰가요?
//                                minus(textView, position, optionModel, optionMenuModel)
//                            }
//                        }
//                    }
                }
            })

            mandatoryTwoMenuAdapter.submitList(currentOptionList)
        }
    }

    // 현재 선택한 수량을 반환
    fun checkCurrentAmount(): Int {
        currentCheckCount = 0
        for(i in 0 until currentOptionList.size) {
            currentCheckCount += currentOptionList[i].selectedCount
        }
        return currentCheckCount
    }

    // 수량 +
    fun plus(textView: TextView, position: Int, optionModel: MenuOptionModel.OptionModel, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
        currentOptionList[position].selectedCount = ++optionMenuModel.selectedCount
        textView.text = currentOptionList[position].selectedCount.toString()
        mandatoryTwoMenuAdapter.submitList(currentOptionList)
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
    }

    // 수량 -
    fun minus(textView: TextView, position: Int, optionModel: MenuOptionModel.OptionModel, optionMenuModel: MenuOptionModel.OptionModel.OptionMenuModel) {
        currentOptionList[position].selectedCount = --optionMenuModel.selectedCount
        textView.text = currentOptionList[position].selectedCount.toString()
        mandatoryTwoMenuAdapter.submitList(currentOptionList)
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
    }

    private lateinit var onOptionUpdateListener: OnOptionUpdateListener
    fun setOnOptionUpdateListener(onOptionUpdateListener: OnOptionUpdateListener) {
        this.onOptionUpdateListener = onOptionUpdateListener
    }
}