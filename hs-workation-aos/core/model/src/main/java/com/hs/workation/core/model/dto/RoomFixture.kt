package com.hs.workation.core.model.dto

data class RoomFixture(
    var id: String? = null,
    var roomId: String? = null,
    var fixtureId: String? = null,
    var status: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)