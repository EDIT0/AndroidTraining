package com.hs.workation.core.model.dto

data class UpdatePayment(
    var reservationId: String? = null,
    var memberId: String? = null,
    var paymentCardId: String? = null,
    var status: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null,
    var outstandingAmount: Int? = null,
    var remark: String? = null,
    var reason: String? = null,
    var categoryId: String? = null
)