package com.hs.workation.core.model.dto

data class Coupon(
    var id: String? = null,
    var name: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var description: String? = null,
    var status: String? = null,
    var issuedCount: Int? = null,
    var couponProfileId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)