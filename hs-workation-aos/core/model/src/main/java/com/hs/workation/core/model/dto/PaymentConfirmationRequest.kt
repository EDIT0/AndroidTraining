package com.hs.workation.core.model.dto

data class PaymentConfirmationRequest(
    var paymentCardId: String? = null,
    var issuedCouponId: String? = null
)