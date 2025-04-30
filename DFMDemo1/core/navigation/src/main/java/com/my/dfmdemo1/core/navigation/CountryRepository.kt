package com.my.dfmdemo1.core.navigation

import android.content.Context
import com.my.dfmdemo1.core.util.PreferencesUtil

object CountryRepository {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
        country = PreferencesUtil.getString(
            context,
            PreferencesUtil.KEY_COUNTRY,
            CountryName.Common.name
        ) ?: CountryName.Common.name
    }

    var country: String = CountryName.Common.name

}