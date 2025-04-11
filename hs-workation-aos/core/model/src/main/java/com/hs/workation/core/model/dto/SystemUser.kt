package com.hs.workation.core.model.dto

data class SystemUser(
    var id: String? = null,
    var company: Company? = null,
    var roles: List<Role>? = null,
    var email: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var lastPasswordModifiedDate: String? = null,
    var status: String? = null,
    var description: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
) {
    data class Company(
        var id: String? = null,
        var businessNumber: String? = null,
        var name: String? = null,
        var division: String? = null,
        var managerName: String? = null,
        var managerRank: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null,
        var description: String? = null
    )
    data class Role(
        var name: String? = null,
        var description: String? = null
    )
}