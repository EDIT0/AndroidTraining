package com.my.dfmdemo1.core.navigation

import android.content.Context
import com.my.dfmdemo1.core.util.LocaleHelper
import com.my.dfmdemo1.core.util.PreferencesUtil

object CountryRepository {

    fun init(context: Context) {
        country = PreferencesUtil.getString(
            context.applicationContext,
            PreferencesUtil.KEY_COUNTRY,
            CountryName.Common.name
        )
    }

    fun languageSetting(context: Context): Context {
        language = PreferencesUtil.getString(
            context.applicationContext,
            PreferencesUtil.KEY_LANGUAGE,
            Language.Common.name
        )

        // en, ko, ja, zh
        var languageCode = ""
        when(language) {
            Language.Ko.name -> {
                languageCode = "ko"
            }
            Language.Ja.name -> {
                languageCode = "ja"
            }
            Language.Zh.name -> {
                languageCode = "zh"
            }
            else -> {
                languageCode = "en"
            }
        }

        val newContext = LocaleHelper.setLocale(context, languageCode)

        return newContext
    }

    var country: String = CountryName.Common.name
    var language: String = Language.Common.name

}