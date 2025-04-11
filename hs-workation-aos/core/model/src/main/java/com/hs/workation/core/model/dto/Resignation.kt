package com.hs.workation.core.model.dto

data class Resignation(
    var id: String? = null,
    var memberId: String? = null,
    var reason: String? = null,
    var status: String? = null,
    var channel: String? = null,
    var company: Company? = null,
    var resident: Resident? = null,
    var member: Member? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null,
    var applyDate: String? = null
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

    data class Member(
        var id: String? = null,
        var name: String? = null,
        var email: String? = null,
        var lastPasswordModifiedDate: String? = null,
        var phoneNumberStatus: String? = null,
        var phoneNumberVerificationDate: String? = null,
        var emailVerificationDate: String? = null,
        var emailStatus: String? = null,
        var phoneNumber: String? = null,
        var status: String? = null,
        var sex: String? = null,
        var birthday: String? = null,
        var createdBy: String? = null,
        var modifiedBy: String? = null,
        var createdDate: String? = null,
        var modifiedDate: String? = null,
        var isDeleted: Boolean? = null
    )
}