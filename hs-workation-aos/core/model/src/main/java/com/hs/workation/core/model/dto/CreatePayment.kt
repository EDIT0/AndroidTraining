package com.hs.workation.core.model.dto

data class CreatePayment(
    var reservationId: String? = null,
    var paymentCardId: String? = null,
    var memberId: String? = null,
    var billings: List<Billing>? = null,
    var categoryId: String? = null
) {
    data class Billing(
        var issuedCouponId: String? = null,
        var categoryId: String? = null,
        var description: String? = null,
        var originalAmount: Int? = null,
        var savingsAmount: Int? = null,
        var finalAmount: Int? = null,
        var outstandingAmount: Int? = null
    )
}