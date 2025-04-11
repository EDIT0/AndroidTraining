package com.hs.workation.core.model.dto

data class CreateMemo(
    var targetId: String? = null,
    var type: String? = null,
    var description: String? = null
)