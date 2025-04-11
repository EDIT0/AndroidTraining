package com.hs.workation.core.model.dto

data class UpdateTerminal(
    var terminalNumber: String? = null,
    var status: String? = null,
    var vehicleId: String? = null
)