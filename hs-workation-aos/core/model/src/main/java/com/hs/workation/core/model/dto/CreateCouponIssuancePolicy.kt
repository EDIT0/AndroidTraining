package com.hs.workation.core.model.dto

data class CreateCouponIssuancePolicy(
    var couponId: String? = null,
    var issueCycle: String? = null,
    var issueScope: String? = null,
    var issueQuantity: Int? = null,
    var status: String? = null
)