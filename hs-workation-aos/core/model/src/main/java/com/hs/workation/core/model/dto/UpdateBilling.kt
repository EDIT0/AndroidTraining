package com.hs.workation.core.model.dto

data class UpdateBilling(
    var paymentId: String? = null,
    var issuedCouponId: String? = null,
    var categoryId: String? = null,
    var description: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null,
    var outstandingAmount: Int? = null
)