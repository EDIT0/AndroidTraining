package com.hs.workation.core.model.dto

data class UpdateTrip(
    var tripName: String? = null,
    var tripNumber: String? = null,
    var categoryId: String? = null,
    var tripTime: String? = null,
    var description: String? = null,
    var status: String? = null
)