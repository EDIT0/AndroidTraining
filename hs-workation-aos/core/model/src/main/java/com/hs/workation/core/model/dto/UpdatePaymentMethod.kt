package com.hs.workation.core.model.dto

data class UpdatePaymentMethod(
    var paymentCardId: String? = null,
    var isDefault: Boolean? = null,
    var status: String? = null
)