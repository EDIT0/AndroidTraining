package com.hs.workation.core.model.dto

data class ShuttleFixture(
    var id: String? = null,
    var shuttleId: String? = null,
    var fixtureId: String? = null,
    var status: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)