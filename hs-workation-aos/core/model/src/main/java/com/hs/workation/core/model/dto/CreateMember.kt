package com.hs.workation.core.model.dto

data class CreateMember(
    var phoneNumber: String? = null,
    var password: String? = null,
    var email: String? = null,
    var termIDs: Long? = null,
    var termId: String? = null,
    var birthday: String? = null,
    var name: String? = null,
    var sex: String? = null
)