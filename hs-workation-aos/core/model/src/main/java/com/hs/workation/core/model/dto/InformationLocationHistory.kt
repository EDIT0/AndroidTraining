package com.hs.workation.core.model.dto

data class InformationLocationHistory(
    var id: String? = null,
    var memberId: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var model: String? = null,
    var numberPlate: String? = null,
    var lon: String? = null,
    var lat: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)