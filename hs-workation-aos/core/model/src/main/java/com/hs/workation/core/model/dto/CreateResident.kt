package com.hs.workation.core.model.dto

data class CreateResident(
    var region: String? = null,
    var files: List<ResidentFile>? = null
)