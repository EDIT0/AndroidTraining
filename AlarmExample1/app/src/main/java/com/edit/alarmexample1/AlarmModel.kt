package com.edit.alarmexample1

data class AlarmModel(
    var alarmType: AlarmType,
    var id: Int,
    var finishDate: String,
    var remainingTime: Long,
    var isSwitch: Boolean,
    var repeatTime: Long = 0
) {
}