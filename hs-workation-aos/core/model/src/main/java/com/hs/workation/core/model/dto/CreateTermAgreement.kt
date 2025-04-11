package com.hs.workation.core.model.dto

data class CreateTermAgreement(
    var termId: String? = null,
    var memberId: String? = null,
    var isAgree: Boolean? = null
)