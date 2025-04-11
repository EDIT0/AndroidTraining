package com.hs.workation.core.model.dto

data class CreateRoomFile(
    var roomId: String? = null,
    var type: String? = null,
    var file: java.io.File? = null,
    var isAppVisible: Boolean? = null
)