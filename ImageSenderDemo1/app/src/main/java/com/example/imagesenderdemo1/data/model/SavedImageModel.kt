package com.example.imagesenderdemo1.data.model

import com.google.gson.annotations.SerializedName

data class SavedImageModel(
    @SerializedName("idx")
    val idx: Int,
    @SerializedName("image_address")
    val imageAddress: String
) {
}