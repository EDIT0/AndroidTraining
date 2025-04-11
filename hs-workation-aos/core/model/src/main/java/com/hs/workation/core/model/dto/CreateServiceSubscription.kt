package com.hs.workation.core.model.dto

data class CreateServiceSubscription(
    var memberId: String? = null,
    var associatedServiceId: String? = null
)