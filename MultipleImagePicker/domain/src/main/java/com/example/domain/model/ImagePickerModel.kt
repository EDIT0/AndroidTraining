package com.example.domain.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ImagePickerModel(
    @SerializedName("uri")
    var uri: Uri,
    @SerializedName("isChecked")
    var isChecked: Boolean
) : Parcelable, ImagePickerTypeModel {

}