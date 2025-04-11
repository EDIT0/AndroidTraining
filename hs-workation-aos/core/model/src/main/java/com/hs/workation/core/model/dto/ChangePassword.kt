package com.hs.workation.core.model.dto

data class ChangePassword(
    var id: String? = null,
    var password: String? = null,
    var confirmPassword: String? = null,
    var authCode: String? = null
)