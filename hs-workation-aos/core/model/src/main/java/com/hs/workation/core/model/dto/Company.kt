package com.hs.workation.core.model.dto

data class Company(
    var id: String? = null,
    var businessNumber: String? = null,
    var name: String? = null,
    var addressLine1: String? = null,
    var addressLine2: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var zipCode: String? = null,
    var numberOfMembers: Int? = null,
    var division: String? = null,
    var status: String? = null,
    var managerName: String? = null,
    var managerRank: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var description: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)