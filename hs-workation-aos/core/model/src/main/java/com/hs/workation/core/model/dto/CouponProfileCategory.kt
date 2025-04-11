package com.hs.workation.core.model.dto

data class CouponProfileCategory(
    var id: String? = null,
    var couponProfileId: String? = null,
    var categoryId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)