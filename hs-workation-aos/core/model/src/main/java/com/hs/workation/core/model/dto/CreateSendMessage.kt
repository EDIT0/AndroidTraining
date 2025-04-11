package com.hs.workation.core.model.dto

data class CreateSendMessage(
    var messageMember: MessageMember? = null,
    var phoneNumber: String? = null,
    var email: String? = null,
    var file: File? = null,
    var channel: String? = null,
    var primaryServiceLevel: String? = null,
    var title: String? = null,
    var context: String? = null,
    var messageType: String? = null
) {

    data class MessageMember(
        var memberId: String? = null,
        var name: String? = null
    )
    data class File(
        var file: FileData? = null
    )
    data class FileData(
        var extension: String? = null,
        var filename: String? = null,
        var blob: java.io.File? = null,
        var isAppVisible: Boolean? = null
    )
}