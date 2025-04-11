package com.hs.workation.core.model.dto

data class UpdateCoupon(
    var name: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var description: String? = null,
    var couponProfileId: String? = null,
    var issuedCount: Int? = null,
    var status: String? = null
)