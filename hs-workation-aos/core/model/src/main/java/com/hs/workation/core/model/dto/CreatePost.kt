package com.hs.workation.core.model.dto

data class CreatePost(
    var postType: String? = null,
    var title: String? = null,
    var content: String? = null,
    var service: String? = null,
    var postDate: String? = null,
    var status: String? = null
)