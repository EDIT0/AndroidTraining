package com.hs.workation.core.model.dto

data class RoomFareDiscountPolicy(
    var id: String? = null,
    var roomFarePlanId: String? = null,
    var timeUnit: String? = null,
    var elapsedTIme: Int? = null,
    var percent: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)