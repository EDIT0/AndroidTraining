package com.hs.workation.core.model.dto

data class SendMessage(
    var id: String? = null,
    var messageId: String? = null,
    var messageMember: MessageMember? = null,
    var title: String? = null,
    var context: String? = null,
    var fileUrl: String? = null,
    var status: String? = null,
    var channel: String? = null,
    var primaryServiceLevel: String? = null,
    var createdBy: String? = null,
    var modifiedBy: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var isDeleted: Boolean? = null
) {

    data class MessageMember(
        var memberId: String? = null,
        var name: String? = null
    )
}