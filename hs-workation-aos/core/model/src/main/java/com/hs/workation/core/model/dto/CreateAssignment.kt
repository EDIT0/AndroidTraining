package com.hs.workation.core.model.dto

data class CreateAssignment(
    var scheduleId: String? = null,
    var systemUserId: String? = null,
    var assignmentDate: String? = null
)