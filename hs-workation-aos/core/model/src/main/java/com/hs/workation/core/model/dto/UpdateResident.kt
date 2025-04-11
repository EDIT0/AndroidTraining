package com.hs.workation.core.model.dto

data class UpdateResident(
    var region: String? = null,
    var files: List<ResidentFile>? = null,
    var status: String? = null
)