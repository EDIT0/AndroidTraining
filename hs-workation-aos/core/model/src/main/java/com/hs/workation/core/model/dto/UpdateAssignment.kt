package com.hs.workation.core.model.dto

data class UpdateAssignment(
    var scheduleId: String? = null,
    var systemUserId: String? = null,
    var assignmentDate: String? = null,
    var status: String? = null
)