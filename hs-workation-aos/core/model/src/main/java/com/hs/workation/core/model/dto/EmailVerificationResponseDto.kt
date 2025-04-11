package com.hs.workation.core.model.dto

data class EmailVerificationResponseDto(
    var isVerified: Boolean? = null,
    var message: String? = null,
    var verifiedDate: String? = null
)