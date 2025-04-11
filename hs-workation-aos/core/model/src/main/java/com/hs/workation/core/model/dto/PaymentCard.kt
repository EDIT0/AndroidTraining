package com.hs.workation.core.model.dto

data class PaymentCard(
    var id: String? = null,
    var memberId: String? = null,
    var cardNumber: String? = null,
    var billingKey: String? = null,
    var vendor: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)