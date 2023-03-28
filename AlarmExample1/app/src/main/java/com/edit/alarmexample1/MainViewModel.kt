package com.edit.alarmexample1

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(
    private val app: Application
): AndroidViewModel(app) {
    var year: Int = -1
    var month = -1
    var day = -1
    var hour = -1
    var minute = -1
    var seconds = -1

    private val _alarmSaveObserver = SingleLiveEvent<String>()
    val alarmSaveObserver: LiveData<String> get() = _alarmSaveObserver

    fun saveAlarm() {
        _alarmSaveObserver.call()
    }

    private val _alarmList = MutableLiveData<MutableList<AlarmModel>>(mutableListOf())
    val alarmList: LiveData<MutableList<AlarmModel>> = _alarmList

    fun setAlarmList(list: ArrayList<AlarmModel>) {
        _alarmList.value = list
    }

    fun changeAlarmList(alarmModel: AlarmModel, position: Int) {
        _alarmList.value?.set(position, alarmModel)
        _alarmList.value = _alarmList.value?.toMutableList()
    }

    fun addAlarmList(model: AlarmModel) {
        _alarmList.value?.add(model)
    }

    fun deleteOneTimeAlarm(alarmModel: AlarmModel) {
        _alarmList.value?.remove(alarmModel)
        PreferencesManager.putAlarmList(app, alarmList.value as ArrayList<AlarmModel>)
        _alarmList.value = _alarmList.value?.toMutableList()
    }

    fun saveOneTimeAlarm(alarmModel: AlarmModel, position: Int) {
        _alarmList.value?.add(position, alarmModel)
        PreferencesManager.putAlarmList(app, alarmList.value as ArrayList<AlarmModel>)
        _alarmList.value = _alarmList.value?.toMutableList()
    }

    val random = Random()
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    var id = -1


}