package com.hs.workation.core.model.dto

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
    var ForcedApprovalId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)