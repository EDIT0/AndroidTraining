package com.my.dfmdemo1.core.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {

    private const val PREF_NAME = "my_app_prefs"
    private const val MODE = Context.MODE_PRIVATE

    const val KEY_COUNTRY = "KEY_COUNTRY"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, MODE)
    }

    fun putString(context: Context, key: String, value: String) {
        getPreferences(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String): String {
        return getPreferences(context).getString(key, defaultValue)?:""
    }

    fun remove(context: Context, key: String) {
        getPreferences(context).edit().remove(key).apply()
    }

    fun clearAll(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}