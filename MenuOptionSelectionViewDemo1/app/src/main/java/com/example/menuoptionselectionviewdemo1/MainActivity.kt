package com.example.menuoptionselectionviewdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.menuoptionselectionviewdemo1.databinding.ActivityMainBinding
import com.example.menuoptionselectionviewdemo1.option.callback.IsMenuOrderPossibleCallback
import com.example.menuoptionselectionviewdemo1.option.callback.OnBackClickListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnSelectedMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel
import com.example.menuoptionselectionviewdemo1.option.model.SelectedMenuOptionModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val sampleMenuOptions = MenuOptionModel(
        menuPrice = 1000, // 메뉴 가격 예시
        menuCount = 1,
        thumbnail = "https://i.namu.wiki/i/WPjverpzRvIjR-lxCgvg040uN2kUP3kpVURuOjG2TkKHsZ-Me517U9xdMjyXuWTncO0vVNOK6X_Aaf_6G-9WsTgw8o4_78E7pKDnenuozKMGKpPMws0g2QkKNWBysdG7ikeXyk-N1bFaYrnGd4uf0A.webp",
        menuTitle = "옹기종기팩",
        menuSubTitle = "친구, 동료들과 옹기종기! 8가지 맛 싱글레귤러를 한 팩에!",
        optionList = listOf(
            MenuOptionModel.OptionModel(
                isMandatorySelection = false,
                optionTitle = "옵션 1",
                amount = 2,
                isMaximumAmount = false,
                isMinimumAmount = false,
                maximumAmount = 0,
                minimumAmount = 0,
                menuList = List(5) { index ->
                    MenuOptionModel.OptionModel.OptionMenuModel(
                        optionMenuTitle = if(index == 0 || index == 1) {
                            "메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index 메뉴 $index "
                        } else {
                            "메뉴 $index"
                        },
                        remainAmount = if(index == 0 || index == 2) {
                            null
                        } else {
                            index
                        },
                        additionalPrice = if(index == 0 || index == 3) {
                            null
                        } else {
                            index * 100
                        },
                        isSoldOut = if(index == 0 || index == 2) {
                            true
                        } else {
                            false
                        }
                    )
                }
            ),
            MenuOptionModel.OptionModel(
                isMandatorySelection = true,
                optionTitle = "옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2 옵션 2",
                amount = 3,
                isMaximumAmount = true,
                isMinimumAmount = true,
                maximumAmount = 4,
                minimumAmount = 2,
                menuList = List(5) { index ->
                    MenuOptionModel.OptionModel.OptionMenuModel(
                        optionMenuTitle = "메뉴 ${index + 5}",
                        remainAmount = (index + 5) % 3,
                        additionalPrice = (index + 5) * 100
                    )
                }
            ),
            MenuOptionModel.OptionModel(
                isMandatorySelection = true,
                optionTitle = "옵션 3",
                amount = 2,
                isMaximumAmount = true,
                isMinimumAmount = false,
                maximumAmount = 3,
                minimumAmount = 0,
                menuList = List(5) { index ->
                    MenuOptionModel.OptionModel.OptionMenuModel(
                        optionMenuTitle = if(index == 0 || index == 1) {
                            "메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} "
                        } else {
                            "메뉴 ${index + 10}"
                        },
                        remainAmount = (index + 10) % 3,
                        additionalPrice = if(index == 0 || index == 2) {
                            null
                        } else {
                            -(index + 10) * 100
                        }
                    )
                }
            ),
            MenuOptionModel.OptionModel(
                isMandatorySelection = false,
                optionTitle = "옵션 4",
                amount = 3,
                isMaximumAmount = true,
                isMinimumAmount = true,
                maximumAmount = 3,
                minimumAmount = 2,
                menuList = List(5) { index ->
                    MenuOptionModel.OptionModel.OptionMenuModel(
                        optionMenuTitle = if(index == 0 || index == 1) {
                            "메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} 메뉴 ${index + 10} "
                        } else {
                            "메뉴 ${index + 10}"
                        },
                        remainAmount = (index + 10) % 3,
                        additionalPrice = if(index == 0 || index == 2) {
                            null
                        } else {
                            (index + 10) * 100
                        }
                    )
                }
            ),
            MenuOptionModel.OptionModel(
                isMandatorySelection = false,
                optionTitle = "옵션 5",
                amount = 2,
                isMaximumAmount = true,
                isMinimumAmount = false,
                maximumAmount = 3,
                minimumAmount = 0,
                menuList = List(5) { index ->
                    MenuOptionModel.OptionModel.OptionMenuModel(
                        optionMenuTitle = if(index == 0 || index == 1) {
                            "메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} 메뉴 ${index + 20} "
                        } else {
                            "메뉴 ${index + 20}"
                        },
                        remainAmount = (index + 10) % 3,
                        additionalPrice = if(index == 0 || index == 4) {
                            null
                        } else {
                            (index + 10) * 15
                        }
                    )
                }
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.menuHomeView.apply {
            setView(sampleMenuOptions)
            setOnBackClickListener(object : OnBackClickListener {
                override fun onBackClick() {
                    finish()
                }
            })
            setOnMenuOptionUpdateListener(object : OnMenuOptionUpdateListener {
                override fun onMenuOptionUpdate(menuOptionModel: MenuOptionModel) {
                    val gson = Gson()
                    Log.d("MYTAG", "MainActivity onMenuOptionUpdate() 결과 값: ${gson.toJson(menuOptionModel)}")
                }
            })
            setOnSelectedMenuOptionUpdateListener(object : OnSelectedMenuOptionUpdateListener {
                override fun onSelectedMenuOptionUpdate(selectedMenuOptionModel: SelectedMenuOptionModel) {
                    val gson = Gson()
                    Log.d("MYTAG", "MainActivity onSelectedMenuOptionUpdate() 결과 값: ${gson.toJson(selectedMenuOptionModel)}")
                }
            })
            setIsMenuOrderPossibleCallback(object : IsMenuOrderPossibleCallback {
                override fun menuOrderPossible(isPossible: Boolean) {
                    Log.d("MYTAG", "MainActivity IsMenuOrderPossibleCallback() 결과 값: ${isPossible}")
                }
            })
        }
    }
}