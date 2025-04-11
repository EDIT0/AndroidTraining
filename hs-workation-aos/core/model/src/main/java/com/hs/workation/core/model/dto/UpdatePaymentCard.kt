package com.hs.workation.core.model.dto

data class UpdatePaymentCard(
    var cardNumber: String? = null,
    var billingKey: String? = null,
    var vendor: String? = null,
    var status: String? = null
)