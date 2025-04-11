package com.hs.workation.core.model.dto

data class UpdateVehicleFareDiscountPolicy(
    var vehicleFarePlanId: String? = null,
    var timeUnit: String? = null,
    var elapsedTime: Int? = null,
    var percent: Int? = null
)