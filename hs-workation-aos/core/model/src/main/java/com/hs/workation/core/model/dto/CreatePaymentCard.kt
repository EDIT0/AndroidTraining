package com.hs.workation.core.model.dto

data class CreatePaymentCard(
    var cardNumber: String? = null,
    var vendor: String? = null,
    var expireYear: String? = null,
    var expireMonth: String? = null,
    var cardPw: String? = null,
    var idNo: String? = null
)