package com.my.cardocrdemo1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardInfoModel(
    val number: String,
    val date: String
) : Parcelable