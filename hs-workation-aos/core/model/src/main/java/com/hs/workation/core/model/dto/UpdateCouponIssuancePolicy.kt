package com.hs.workation.core.model.dto

data class UpdateCouponIssuancePolicy(
    var couponId: String? = null,
    var issueCycle: String? = null,
    var issueScope: String? = null,
    var issueQuantity: Int? = null
)