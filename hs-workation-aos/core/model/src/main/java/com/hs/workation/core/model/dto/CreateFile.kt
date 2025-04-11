package com.hs.workation.core.model.dto

data class CreateFile(
    var extension: String? = null,
    var filename: String? = null,
    var blob: java.io.File? = null
)