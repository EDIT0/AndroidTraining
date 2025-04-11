package com.hs.workation.core.model.dto

data class SendMessageHistory(
    var id: String? = null,
    var sendMessageId: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
)