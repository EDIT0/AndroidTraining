package com.hs.workation.core.model.dto

data class VerifyEmail(
    var email: String? = null,
    var authCode: String? = null
)