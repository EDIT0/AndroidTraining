package com.hs.workation.core.model.dto

data class Complaint(
    var id: String? = null,
    var systemUserId: String? = null,
    var systemUserName: String? = null,
    var answer: String? = null,
    var member: String? = null,
    var complaintType: String? = null,
    var channel: String? = null,
    var status: String? = null,
    var question: String? = null,
    var primaryComplaintLevel: String? = null,
    var secondaryComplaintLevel: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var files: String? = null,
    var fileUrl: String? = null
)
