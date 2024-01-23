package com.example.menuoptionselectionviewdemo1.option.model

data class MenuOptionModel(
    val menuPrice: Int,
    var menuCount: Int,
    var thumbnail: String,
    val menuTitle: String,
    val menuSubTitle: String,
    var optionList: List<OptionModel>
) {
    data class OptionModel(
//        val selectionViewType: Int,
        val isMandatorySelection: Boolean, // 뷰 타입 결정에 있어서 필수 값
        val optionTitle: String,
        val amount: Int, // 뷰 타입 결정에 있어서 필수 값
        val isMaximumAmount: Boolean,
        val isMinimumAmount: Boolean,
        val maximumAmount: Int,
        val minimumAmount: Int,
        var menuList: List<OptionMenuModel>
    ) {
        data class OptionMenuModel(
            val optionMenuTitle: String,
            val remainAmount: Int? = null,
            val additionalPrice: Int? = null,
            var selectedCount: Int = 0,
            val isSoldOut: Boolean = false
        ) {

        }
    }
}