package com.hs.workation.core.model.dto

data class CreatePaymentMethod(
    var paymentCardId: String? = null,
    var isDefault: Boolean? = null
)