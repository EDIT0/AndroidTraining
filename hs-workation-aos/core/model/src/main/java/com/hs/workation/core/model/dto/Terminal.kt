package com.hs.workation.core.model.dto

data class Terminal(
    var terminalId: String? = null,
    var terminalNumber: String? = null,
    var status: String? = null,
    var vehicleId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)