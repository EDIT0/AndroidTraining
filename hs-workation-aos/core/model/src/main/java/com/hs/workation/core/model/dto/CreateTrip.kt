package com.hs.workation.core.model.dto

data class CreateTrip(
    var tripName: String? = null,
    var tripNumber: String? = null,
    var categoryId: String? = null,
    var tripTime: Int? = null,
    var maxTripTime: Int? = null,
    var description: String? = null,
    var status: String? = null
)