package com.hs.workation.core.model.dto

data class UpdateIssuedCoupon(
    var id: String? = null,
    var expirationDate: String? = null,
    var issuedType: String? = null,
    var couponProfileId: String? = null,
    var couponId: String? = null,
    var memberId: String? = null,
    var code: String? = null,
    var isUsed: Boolean? = null,
    var issuedBy: String? = null,
    var isDeleted: Boolean? = null
)