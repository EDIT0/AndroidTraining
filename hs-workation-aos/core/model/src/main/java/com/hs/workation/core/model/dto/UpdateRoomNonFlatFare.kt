package com.hs.workation.core.model.dto

data class UpdateRoomNonFlatFare(
    var roomFarePlanId: String? = null,
    var date: String? = null,
    var fare: Int? = null
)