package com.hs.workation.core.model.dto

data class UpdateCouponProfile(
    var name: String? = null,
    var type: String? = null,
    var amount: Int? = null,
    var percentage: Double? = null,
    var description: String? = null,
    var status: String? = null
)