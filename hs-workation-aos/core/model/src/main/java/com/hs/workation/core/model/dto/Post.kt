package com.hs.workation.core.model.dto

data class Post(
    var id: String? = null,
    var postType: String? = null,
    var title: String? = null,
    var content: String? = null,
    var service: String? = null,
    var postDate: String? = null,
    var status: String? = null,
    var systemUserId: String? = null,
    var createdDate: String? = null,
    var createdBy: String? = null,
    var modifiedDate: String? = null,
    var modifiedBy: String? = null,
    var isDeleted: Boolean? = null
)