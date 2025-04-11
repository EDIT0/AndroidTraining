package com.hs.workation.core.model.dto

data class PaymentMethod(
    var id: String? = null,
    var memberId: String? = null,
    var paymentCardId: String? = null,
    var isDefault: Boolean? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)