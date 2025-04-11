package com.hs.workation.core.model.dto

data class VerifyPhoneNumber(
    var phoneNumber: String? = null,
    var authCode: String? = null
)