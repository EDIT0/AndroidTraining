package com.hs.workation.core.model.dto

data class CreateTerminal(
    var terminalNumber: String? = null,
    var status: String? = null,
    var vehicleId: String? = null
)