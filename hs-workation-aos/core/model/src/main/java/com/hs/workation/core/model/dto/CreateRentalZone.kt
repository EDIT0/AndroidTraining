package com.hs.workation.core.model.dto

data class CreateRentalZone(
    var status: String? = null,
    var name: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zipCode: String? = null,
    var description: String? = null
)