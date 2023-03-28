package com.edit.alarmexample1

data class AlarmModel(
    var alarmType: AlarmType,
    var id: Int,
    var finishDate: String,
    var time: Long,
    var isSwitch: Boolean
) {
}