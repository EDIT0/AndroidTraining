package com.hs.workation.core.model.dto

data class UpdateResignation(
    var channel: String? = null,
    var reason: String? = null,
    var status: String? = null
)