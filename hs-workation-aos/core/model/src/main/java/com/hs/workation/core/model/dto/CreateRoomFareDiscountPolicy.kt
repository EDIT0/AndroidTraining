package com.hs.workation.core.model.dto

data class CreateRoomFareDiscountPolicy(
    var roomFarePlanId: String? = null,
    var timeUnit: String? = null,
    var elapsedTIme: Int? = null,
    var percent: Int? = null
)