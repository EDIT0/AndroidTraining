package com.hs.workation.core.model.dto

data class Workcation(
    var id: String? = null,
    var name: String? = null,
    var uri: String? = null,
    var status: String? = null,
    var phoneNumber: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)