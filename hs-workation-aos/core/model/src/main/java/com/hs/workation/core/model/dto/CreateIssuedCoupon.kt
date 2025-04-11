package com.hs.workation.core.model.dto

data class CreateIssuedCoupon(
    var expirationDate: String? = null,
    var issuedType: String? = null,
    var couponProfileId: String? = null,
    var couponId: String? = null,
    var memberId: String? = null,
    var code: String? = null,
    var isUsed: Boolean? = null,
    var issuedBy: String? = null
)