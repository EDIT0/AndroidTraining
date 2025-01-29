package com.my.customviewdemo1.dto

data class DaySelectionModel(
    val idx: Int,
    val year: Int,
    val month: Int,
    val day: Int,
    var isClicked: Boolean
)