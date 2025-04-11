package com.hs.workation.core.model.dto

data class UpdateRentalZone(
    var name: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var status: String? = null,
    var description: String? = null
)