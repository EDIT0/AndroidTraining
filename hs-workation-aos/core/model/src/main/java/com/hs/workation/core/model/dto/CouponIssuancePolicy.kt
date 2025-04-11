package com.hs.workation.core.model.dto

data class CouponIssuancePolicy(
    var id: String? = null,
    var couponId: String? = null,
    var issueCycle: String? = null,
    var issueScope: String? = null,
    var status: String? = null,
    var issueQuantity: Int? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)