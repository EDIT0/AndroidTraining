package com.hs.workation.core.model.dto

data class Billing(
    var id: String? = null,
    var paymentId: String? = null,
    var issuedCouponId: String? = null,
    var category: UpdateCategory? = null,
    var description: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null,
    var outstandingAmount: Int? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)