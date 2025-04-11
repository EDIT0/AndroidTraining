package com.hs.workation.core.model.dto

data class CreateMessageFile(
    var messageId: String? = null,
    var type: String? = null,
    var file: java.io.File? = null,
    var isAppVisible: Boolean? = null
)