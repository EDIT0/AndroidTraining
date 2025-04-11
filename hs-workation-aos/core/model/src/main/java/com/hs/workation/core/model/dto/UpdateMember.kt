package com.hs.workation.core.model.dto

data class UpdateMember(
    var name: String? = null,
    var email: String? = null,
    var companyId: String? = null,
    var status: String? = null,
    var sex: String? = null,
    var zipCode: String? = null,
    var birthday: String? = null,
    var addressId: String? = null
)