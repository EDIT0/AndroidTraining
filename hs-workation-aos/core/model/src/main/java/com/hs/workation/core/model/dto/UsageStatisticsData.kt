package com.hs.workation.core.model.dto

data class UsageStatisticsData(
    var primaryServiceLevel: String? = null,
    var secondaryServiceLevel: String? = null,
    var value: Int? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var name: String? = null,
    var type: String? = null,
    var targetId: String? = null
)