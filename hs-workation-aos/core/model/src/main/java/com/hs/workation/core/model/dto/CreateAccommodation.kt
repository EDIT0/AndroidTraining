package com.hs.workation.core.model.dto

data class CreateAccommodation(
    var name: String? = null,
    var type: String? = null,
    var description: String? = null,
    var phoneNumber: String? = null,
    var website: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var latitude: String? = null,
    var longituide: String? = null
)