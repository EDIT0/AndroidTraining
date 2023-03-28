package com.edit.alarmexample1

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferencesManager {

    fun putAlarmList(context: Context, list: ArrayList<AlarmModel>) {
        val sharedPreferences = context.getSharedPreferences("ALARM_MANAGER", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val json = gson.toJson(list)

        editor.putString("alarm_list", json)
        editor.apply()
    }

    fun getAlarmList(context: Context): ArrayList<AlarmModel> {
        val sharedPreferences = context.getSharedPreferences("ALARM_MANAGER", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("alarm_list", null)
        val type = object : TypeToken<ArrayList<AlarmModel>>() {}.type
        val arrayList: ArrayList<AlarmModel>? = gson.fromJson(json, type)
        Log.i("MYTAG", "${arrayList}")
        return arrayList?: ArrayList<AlarmModel>()
    }
}