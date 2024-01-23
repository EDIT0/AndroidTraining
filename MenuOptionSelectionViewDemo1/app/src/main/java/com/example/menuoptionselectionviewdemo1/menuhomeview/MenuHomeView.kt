package com.example.menuoptionselectionviewdemo1.menuhomeview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.menuoptionselectionviewdemo1.R
import com.example.menuoptionselectionviewdemo1.Utils
import com.example.menuoptionselectionviewdemo1.databinding.MenuHomeViewBinding
import com.example.menuoptionselectionviewdemo1.option.callback.IsMenuOrderPossibleCallback
import com.example.menuoptionselectionviewdemo1.option.callback.OnBackClickListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnSelectedMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel
import com.example.menuoptionselectionviewdemo1.option.model.SelectedMenuOptionModel
import com.google.gson.Gson

class MenuHomeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: MenuHomeViewBinding = MenuHomeViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var totalPrice: Long = 0L // 메뉴 + 옵션 합산 금액
    private var isPossibleOrder = false // 주문 가능 여부
    private lateinit var selectedMenuOptionModel: SelectedMenuOptionModel // 주문표 데이터 모델
    private var actionBarHeight = 0

    fun setView(settingMenuOptionModel: MenuOptionModel) {
        // API에서 가져온 데이터 모델 ex) sampleMenuOptions

        totalPrice = settingMenuOptionModel.menuPrice.toLong()
        updateTotalPrice(totalPrice) // 합산 금액 초기화
        setEnableBasketClick(false) // 장바구니 false
        setEnableOrderClick(false) // 주문하기 false
        Glide.with(binding.ivAppbarImage1.context)
            .load(settingMenuOptionModel.thumbnail)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivAppbarImage1)

        buttonClickListener()

        // MenuView 설정
        binding.menuView.apply {
            setMenu(settingMenuOptionModel)
            setOnMenuOptionUpdateListener(object : OnMenuOptionUpdateListener {
                override fun onMenuOptionUpdate(menuOptionModel: MenuOptionModel) {
                    val gson = Gson()
                    Log.d("MYTAG", "전체 최종 업데이트: ${gson.toJson(menuOptionModel)}")
                    selectedMenuOptionModel = menuOptionModel.toSelectedMenuOptionModel()

                    isPossibleOrder = true

                    // 전체 합산 금액 초기화
                    totalPrice = settingMenuOptionModel.menuPrice.toLong()
                    updateTotalPrice(totalPrice)

                    // 메뉴 개수에 따라 금액 계산
                    totalPrice = (menuOptionModel.menuCount * menuOptionModel.menuPrice).toLong()

                    for(i in 0 until menuOptionModel.optionList.size) {
                        val option = menuOptionModel.optionList[i]
                        var selectedCountForMandatorySelection = 0

                        for(j in 0 until option.menuList.size) {
                            val optionMenu = option.menuList[j]
                            // 선택된 가격 변동을 주는 옵션메뉴 개수에 따라 금액 계산
                            totalPrice += optionMenu.additionalPrice?.let {
                                it * optionMenu.selectedCount
                            } ?: 0

                            selectedCountForMandatorySelection += optionMenu.selectedCount
                        }

                        Log.d("MYTAG", "isPossibleOrder: ${isPossibleOrder} ${option.optionTitle}에 대한 옵션메뉴 갯수: ${selectedCountForMandatorySelection}")

                        if(isPossibleOrder) {
                            if(option.isMandatorySelection) {
                                if(option.isMinimumAmount) {
                                    if(option.minimumAmount <= selectedCountForMandatorySelection) { // 현재 옵션메뉴가 min값보다 같거나 큰가요?

                                    } else {
                                        isPossibleOrder = false
                                    }
                                } else if(option.isMaximumAmount) {
                                    if(option.maximumAmount >= selectedCountForMandatorySelection && 1 <= selectedCountForMandatorySelection) { // 현재 옵션메뉴가 max값보다 같거나 작은가요? 그리고 1보다 같거나 큰가요?

                                    } else {
                                        isPossibleOrder = false
                                    }
                                } else {
                                    if(option.amount == selectedCountForMandatorySelection) { // 현재 옵션메뉴가 amount 값과 동일한가요?

                                    } else {
                                        isPossibleOrder = false
                                    }
                                }
                            }
                        }
                    }

                    Log.d("MYTAG", "주문 가능? ${isPossibleOrder}")

                    if(isPossibleOrder) {
                        setEnableBasketClick(true)
                        setEnableOrderClick(true)
                    } else {
                        setEnableBasketClick(false)
                        setEnableOrderClick(false)
                    }

                    updateTotalPrice(totalPrice)

                    val orderSheet: SelectedMenuOptionModel = getOrderSheet(selectedMenuOptionModel)
                    onMenuOptionUpdateListener.onMenuOptionUpdate(menuOptionModel)
                    onSelectedMenuOptionUpdateListener.onSelectedMenuOptionUpdate(orderSheet)
                    isMenuOrderPossibleCallback.menuOrderPossible(isPossibleOrder)
                }
            })
        }

        // 액션바 타이틀
        binding.tvRootTitle.text = settingMenuOptionModel.menuTitle

        settingAppBarLayout()
    }

    private fun buttonClickListener() {
        binding.ivBack.setOnClickListener {
            onBackClickListener.onBackClick()
        }

        // 장바구니 클릭
        binding.itemOrderResultView.tvBasket.setOnClickListener {
            Utils.showToast(binding.root.context, "장바구니 클릭")
        }

        // 주문하기 클릭
        binding.itemOrderResultView.tvOrder.setOnClickListener {
            Utils.showToast(binding.root.context, "주문하기 클릭")
        }
    }

    private fun settingAppBarLayout() {
        binding.mToolbar.post {
            Log.d("MYTAG", "액션바 높이 : ${binding.mToolbar.height}")
            actionBarHeight = binding.mToolbar.height
        }

        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(actionBarHeight / 2 >= Math.abs(Math.abs(verticalOffset) - appBarLayout.totalScrollRange)) {
                if(binding.tvRootTitle.visibility == View.GONE) {
                    Log.d("MYTAG", "Show")
                    Glide.with(binding.ivBack.context)
                        .load(R.drawable.ic_arrow_back_ios_new_24_black)
                        .into(binding.ivBack)
                    binding.tvRootTitle.visibility = View.VISIBLE
                    binding.mToolbar.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
                }
            } else {
                if(binding.tvRootTitle.visibility == View.VISIBLE) {
                    Log.d("MYTAG", "Hide")
                    Glide.with(binding.ivBack.context)
                        .load(R.drawable.ic_arrow_back_ios_new_24_white)
                        .into(binding.ivBack)
                    binding.tvRootTitle.visibility = View.GONE
                    binding.mToolbar.setBackgroundColor(ContextCompat.getColor(binding.root.context, android.R.color.transparent))
                }
            }
            if(Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                // Collapsed
//                Log.d("MYTAG", "Collapsed verticalOffset: ${verticalOffset} / appBarLayout.totalScrollRange: ${appBarLayout.totalScrollRange} / ${Math.abs(verticalOffset) - appBarLayout.totalScrollRange}")
            } else {
                // Expanded
//                Log.d("MYTAG", "Expanded verticalOffset: ${verticalOffset} / appBarLayout.totalScrollRange: ${appBarLayout.totalScrollRange} / ${Math.abs(verticalOffset) - appBarLayout.totalScrollRange}")
            }
        }
    }

    private fun updateTotalPrice(price: Long) {
        if(price < 0) {
            binding.itemOrderResultView.tvTotalPrice.text = "0원"
        } else {
            binding.itemOrderResultView.tvTotalPrice.text = "${Utils.formatCurrency(price)}원"
        }
    }

    private fun setEnableBasketClick(isEnable: Boolean) {
        binding.itemOrderResultView.basketLayout.isEnabled = isEnable
        binding.itemOrderResultView.tvBasket.isEnabled = isEnable
        if(isEnable) {
            binding.itemOrderResultView.tvBasket.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue))
        } else {
            binding.itemOrderResultView.tvBasket.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        }
    }

    private fun setEnableOrderClick(isEnable: Boolean) {
        binding.itemOrderResultView.orderLayout.isEnabled = isEnable
        binding.itemOrderResultView.tvOrder.isEnabled = isEnable
        if(isEnable) {
            binding.itemOrderResultView.tvOrder.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        } else {
            binding.itemOrderResultView.tvOrder.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        }
    }

    private fun getOrderSheet(item: SelectedMenuOptionModel): SelectedMenuOptionModel {
        val tmp1 = ArrayList<SelectedMenuOptionModel.SelectedOptionModel>()
        for(i in 0 until item.optionList.size) {
            val selectedOption = item.optionList[i]
            val tmp2 = ArrayList<SelectedMenuOptionModel.SelectedOptionModel.SelectedOptionMenuModel>()
            for(j in 0 until selectedOption.menuList.size) {
                val selectedOptionMenu = selectedOption.menuList[j]
                if(selectedOptionMenu.selectedCount == 0) {
                    tmp2.add(selectedOptionMenu)
                }
            }
            for(k in 0 until tmp2.size) {
                (selectedOption.menuList as MutableList).remove(tmp2[k])
            }
            if(selectedOption.menuList.isEmpty()) {
                tmp1.add(selectedOption)
            }
        }
        for(k in 0 until tmp1.size) {
            (item.optionList as MutableList).remove(tmp1[k])
        }

        val gson = Gson()
        Log.d("MYTAG", "주문한 메뉴만 정리: ${gson.toJson(item)}")

        return item
    }

    // 맵핑 MenuOptionModel -> SelectedMenuOptionModel
    fun MenuOptionModel.toSelectedMenuOptionModel(): SelectedMenuOptionModel {
        return SelectedMenuOptionModel(
            menuPrice = this.menuPrice,
            menuCount = this.menuCount,
            thumbnail = this.thumbnail,
            menuTitle = this.menuTitle,
            menuSubTitle = this.menuSubTitle,
            optionList = this.optionList.map { optionModel ->
                SelectedMenuOptionModel.SelectedOptionModel(
                    isMandatorySelection = optionModel.isMandatorySelection,
                    optionTitle = optionModel.optionTitle,
                    amount = optionModel.amount,
                    isMaximumAmount = optionModel.isMaximumAmount,
                    isMinimumAmount = optionModel.isMinimumAmount,
                    maximumAmount = optionModel.maximumAmount,
                    minimumAmount = optionModel.minimumAmount,
                    menuList = optionModel.menuList.map { optionMenuModel ->
                        SelectedMenuOptionModel.SelectedOptionModel.SelectedOptionMenuModel(
                            optionMenuTitle = optionMenuModel.optionMenuTitle,
                            remainAmount = optionMenuModel.remainAmount,
                            additionalPrice = optionMenuModel.additionalPrice,
                            selectedCount = optionMenuModel.selectedCount,
                            isSoldOut = optionMenuModel.isSoldOut
                        )
                    }
                )
            }
        )
    }

    private lateinit var onMenuOptionUpdateListener: OnMenuOptionUpdateListener
    fun setOnMenuOptionUpdateListener(onMenuOptionUpdateListener: OnMenuOptionUpdateListener) {
        this.onMenuOptionUpdateListener = onMenuOptionUpdateListener
    }

    private lateinit var onSelectedMenuOptionUpdateListener: OnSelectedMenuOptionUpdateListener
    fun setOnSelectedMenuOptionUpdateListener(onSelectedMenuOptionUpdateListener: OnSelectedMenuOptionUpdateListener) {
        this.onSelectedMenuOptionUpdateListener = onSelectedMenuOptionUpdateListener
    }

    private lateinit var isMenuOrderPossibleCallback: IsMenuOrderPossibleCallback
    fun setIsMenuOrderPossibleCallback(isMenuOrderPossibleCallback: IsMenuOrderPossibleCallback) {
        this.isMenuOrderPossibleCallback = isMenuOrderPossibleCallback
    }

    private lateinit var onBackClickListener: OnBackClickListener
    fun setOnBackClickListener(onBackClickListener: OnBackClickListener) {
        this.onBackClickListener = onBackClickListener
    }
}