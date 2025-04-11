package com.hs.workation.core.model.dto

data class UpdateMemo(
    var targetId: String? = null,
    var type: String? = null,
    var description: String? = null
)