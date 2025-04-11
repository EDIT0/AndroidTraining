package com.hs.workation.core.model.dto

data class RoomFile(
    var id: String? = null,
    var roomId: String? = null,
    var type: String? = null,
    var fileId: String? = null,
    var fileUrl: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null,
    var isAppVisible: Boolean? = null
)