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
    var isChecked: Boolean,
    var type: Int = ViewType.ALBUM
) : Parcelable {

}

object ViewType {
    const val CAMERA = 0
    const val ALBUM = 1
}