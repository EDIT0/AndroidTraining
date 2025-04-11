package com.hs.workation.core.model.dto

data class CreateRoomNonFlatFare(
    var roomFarePlanId: String? = null,
    var date: String? = null,
    var fare: Int? = null
)