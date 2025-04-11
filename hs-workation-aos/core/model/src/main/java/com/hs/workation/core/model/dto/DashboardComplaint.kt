package com.hs.workation.core.model.dto

data class DashboardComplaint(
    var primaryComplaintLevel: String? = null,
    var secondaryComplaintLevel: String? = null,
    var count: Int? = null
)