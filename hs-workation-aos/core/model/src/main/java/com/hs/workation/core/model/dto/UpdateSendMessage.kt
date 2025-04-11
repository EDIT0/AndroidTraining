package com.hs.workation.core.model.dto

data class UpdateSendMessage(
    var memberId: String? = null,
    var messageId: String? = null,
    var channel: String? = null,
    var title: String? = null,
    var context: String? = null
)