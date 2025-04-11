package com.hs.workation.core.model.dto

data class CreateWaypointFile(
    var waypointId: String? = null,
    var file: java.io.File? = null,
    var type: String? = null,
    var isAppVisible: Boolean? = null
)