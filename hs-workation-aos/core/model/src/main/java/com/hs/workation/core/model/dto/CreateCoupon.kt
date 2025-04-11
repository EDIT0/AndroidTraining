package com.hs.workation.core.model.dto

data class CreateCoupon(
    var name: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var description: String? = null,
    var couponProfileId: String? = null,
    var status: String? = null
)