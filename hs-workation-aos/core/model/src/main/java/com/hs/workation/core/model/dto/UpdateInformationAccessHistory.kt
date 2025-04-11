package com.hs.workation.core.model.dto

data class UpdateInformationAccessHistory(
    var memberId: String? = null,
    var phoneNumber: String? = null,
    var type: String? = null,
    var description: String? = null,
    var systemUserId: String? = null
)