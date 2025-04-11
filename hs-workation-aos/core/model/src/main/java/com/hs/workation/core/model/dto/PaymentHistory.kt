package com.hs.workation.core.model.dto

data class PaymentHistory(
    var id: String? = null,
    var paymentId: String? = null,
    var reservationId: String? = null,
    var memberId: String? = null,
    var paymentCardId: String? = null,
    var tid: String? = null,
    var bid: String? = null,
    var status: String? = null,
    var originalAmount: Int? = null,
    var savingsAmount: Int? = null,
    var finalAmount: Int? = null,
    var outstandingAmount: Int? = null,
    var remark: String? = null,
    var reason: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var categoryId: String? = null
)