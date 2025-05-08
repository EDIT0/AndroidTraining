package com.hs.workation.core.model.dto.req

data class ReqResignation(
    var channel: String? = null,
    var reason: String? = null,
    var memberId: String? = null
)