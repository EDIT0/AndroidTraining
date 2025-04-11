package com.hs.workation.core.model.dto

data class CreateShuttleFile(
    var shuttleId: String? = null,
    var type: String? = null,
    var file: java.io.File? = null,
    var isAppVisible: Boolean? = null
)