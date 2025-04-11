package com.hs.workation.core.model.dto

data class CreateComplaintFile(
    var complaintId: String? = null,
    var type: String? = null,
    var file: java.io.File? = null,
    var isAppVisible: Boolean? = null
)