package com.hs.workation.core.model.dto

import com.google.gson.annotations.SerializedName

data class Member(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var emailVerificationDate: String? = null,
    var isEmailVerified: Boolean? = null,
    var phoneNumber: String? = null,
    var phoneNumberVerificationDate: String? = null,
    var isPhoneNumberVerified: Boolean? = null,
    var unsubscribeRequestDate: String? = null,
    var unsubscribeCompleteDate: String? = null,
    var unsubscribeReason: String? = null,
    var companyId: String? = null,
    var status: String? = null,
    var sex: String? = null,
    var birthday: String? = null,
    var isResident: Boolean? = null,
    var company: Company? = null,
    var address: Address? = null,
    var driverLicense: DriverLicense? = null,
    var lastPasswordModifiedDate: String? = null,
    var phoneNumberStatus: String? = null,
    var emailStatus: String? = null,
    var serviceSubscriptions: List<ServiceSubscription>? = null,
    var usages: List<Usage>? = null,
    var resident: Resident? = null,
    var resignation: Resignation? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
){
    data class Company(
        var id: String? = null,
        var businessNumber: String? = null,
        var name: String? = null,
        var division: String? = null,
        var managerName: String? = null,
        var managerRank: String? = null,
        var email: String? = null,
        var phoneNumber: String? = null,
        var description: String? = null,
        var addressLine1: String? = null,
        var addressLine2: String? = null,
        var city: String? = null,
        var country: String? = null,
        var zipCode: String? = null,
        var status: String? = null,
        var fullAddress: String? = null,
        var createdDate: String? = null,
        var createdBy: String? = null,
        var updatedDate: String? = null,
        var updatedBy: String? = null,
        var isDeleted: Boolean? = null,
        var valid: Boolean? = null
    )
    data class Address(
        var id: String? = null,
        var addressLine1: String? = null,
        var addressLine2: String? = null,
        var city: String? = null,
        var state: String? = null,
        var country: String? = null,
        var zipCode: String? = null,
        var priority: Int? = null
    )

    data class DriverLicense(
        var id: String? = null,
        var licenseNation: String? = null,
        var licenseClass: String? = null,
        var licenseArea: String? = null,
        var licenseNumber: String? = null,
        var issuedDate: String? = null,
        var expireDate: String? = null,
        var confirmDate: String? = null,
        var signatureUrl: String? = null,
        var isForcedApproval: Boolean? = null,
        @SerializedName("ForcedApprovalId") var forcedApprovalId: String? = null
    )

    data class ServiceSubscription(
        var id: String? = null,
        var associatedServiceId: String? = null,
        var primaryServiceLevel: String? = null,
        var member: String? = null,
        var status: String? = null,
        var systemUserId: String? = null,
        var applyDate: String? = null,
        var createdDate: String? = null,
        var createdBy: String? = null,
        var modifiedDate: String? = null,
        var modifiedBy: String? = null,
        var isDeleted: Boolean? = null
    )

    data class Usage(
        var id: String? = null,
        var reservationId: String? = null,
        var targetId: String? = null,
        var status: String? = null,
        var startDate: String? = null,
        var endDate: String? = null,
        var primaryServiceLevel: String? = null,
        var secondaryServiceLevel: String? = null
    )

    data class Resignation(
        var id: String? = null,
        var reason: String? = null,
        var status: String? = null,
        var channel: String? = null,
        var createdDate: String? = null,
        var createdBy: String? = null,
        var modifiedDate: String? = null,
        var modifiedBy: String? = null,
        var isDeleted: Boolean? = null
    )
}