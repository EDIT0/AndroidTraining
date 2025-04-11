package com.hs.workation.core.model.dto

data class CreateResignation(
    var memberId: String? = null,
    var channel: String? = null,
    var reason: String? = null
)