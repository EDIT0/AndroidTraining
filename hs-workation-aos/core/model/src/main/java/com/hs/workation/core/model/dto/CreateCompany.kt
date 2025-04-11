package com.hs.workation.core.model.dto

data class CreateCompany(
    var businessNumber: String? = null,
    var name: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var status: String? = null,
    var division: String? = null,
    var managerName: String? = null,
    var managerRank: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var description: String? = null
)