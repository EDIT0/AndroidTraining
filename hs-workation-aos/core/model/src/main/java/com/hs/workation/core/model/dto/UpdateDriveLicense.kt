package com.hs.workation.core.model.dto

data class UpdateDriveLicense(
    var licenseNation: String? = null,
    var licenseClass: String? = null,
    var licenseArea: String? = null,
    var licensNumber: String? = null,
    var issuedDate: String? = null,
    var expireDate: String? = null,
    var confirmDate: String? = null,
    var signatureUrl: String? = null
)