package com.hs.workation.core.model.dto

data class UpdateContact(
    var phoneNumber: String? = null,
    var description: String? = null,
    var memberId: String? = null
)