package com.hs.workation.core.model.dto

data class CreateSystemUser(
    var companyId: String? = null,
    var roles: List<Role>? = null,
    var status: String? = null,
    var email: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var description: String? = null,
    var password: String? = null
){
    data class Role(
        var name: String? = null,
        var description: String? = null
    )
}