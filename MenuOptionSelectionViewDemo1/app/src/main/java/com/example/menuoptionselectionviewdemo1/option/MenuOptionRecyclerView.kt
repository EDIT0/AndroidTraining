package com.example.menuoptionselectionviewdemo1.option

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menuoptionselectionviewdemo1.option.adapter.MenuOptionAdapter
import com.example.menuoptionselectionviewdemo1.option.callback.OnMenuOptionUpdateListener
import com.example.menuoptionselectionviewdemo1.option.callback.OnOptionUpdateWithPositionListener
import com.example.menuoptionselectionviewdemo1.option.model.MenuOptionModel

class MenuOptionRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    // TODO Adapter, LayoutManager, ItemView

    private val menuOptionAdapter = MenuOptionAdapter()
    private var currentOptionList: ArrayList<MenuOptionModel.OptionModel> = ArrayList<MenuOptionModel.OptionModel>()
    private var currentMenuOptionModel: MenuOptionModel? = null

//    fun setMenuOption(menuPrice: Int, menuCount: Int, thumbnail: String, list: List<MenuOptionModel.OptionModel>) {
//        this.adapter = menuOptionAdapter
//        this.layoutManager = object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
//            override fun canScrollVertically(): Boolean {
//                return true
//            }
//
//            override fun canScrollHorizontally(): Boolean {
//                return false
//            }
//        }
//        currentOptionList = list.toMutableList() as ArrayList<MenuOptionModel.OptionModel>
//
//        menuOptionAdapter.setOnOptionWithPositionListener(object : OnOptionUpdateWithPositionListener {
//            override fun onOptionUpdateWithPosition(
//                optionModel: MenuOptionModel.OptionModel,
//                position: Int
//            ) {
//                currentOptionList[position] = optionModel
//                onMenuOptionUpdateListener.onMenuOptionUpdate(MenuOptionModel(menuPrice, menuCount, thumbnail, currentOptionList))
//            }
//        })
//
//        menuOptionAdapter.submitList(currentOptionList)
//    }

    fun setMenuOption(menuOptionModel: MenuOptionModel) {
        this.adapter = menuOptionAdapter
        this.layoutManager = object : LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return true
            }

            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
        currentMenuOptionModel = menuOptionModel
        currentOptionList = currentMenuOptionModel!!.optionList.toMutableList() as ArrayList<MenuOptionModel.OptionModel>

        menuOptionAdapter.setOnOptionWithPositionListener(object : OnOptionUpdateWithPositionListener {
            override fun onOptionUpdateWithPosition(
                optionModel: MenuOptionModel.OptionModel,
                position: Int
            ) {
                currentOptionList[position] = optionModel
                onMenuOptionUpdateListener.onMenuOptionUpdate(
//                    MenuOptionModel(
//                        menuOptionModel.menuPrice,
//                        menuOptionModel.menuCount,
//                        menuOptionModel.thumbnail,
//                        menuOptionModel.menuTitle,
//                        menuOptionModel.menuSubTitle,
//                        currentOptionList
//                    )
                    currentMenuOptionModel!!
                )
            }
        })

        menuOptionAdapter.submitList(currentOptionList)
    }

    private lateinit var onMenuOptionUpdateListener: OnMenuOptionUpdateListener
    fun setOnMenuOptionUpdateListener(onMenuOptionUpdateListener: OnMenuOptionUpdateListener) {
        this.onMenuOptionUpdateListener = onMenuOptionUpdateListener
    }

}