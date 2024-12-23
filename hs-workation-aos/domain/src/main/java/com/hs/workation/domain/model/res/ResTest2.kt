package com.hs.workation.domain.model.res

data class ResTest2(
    val testPageInfo: TestPageInfo? = null,
    var subjectName: String? = null,
    val subjectNumber: Int? = null,
    val questions: List<TestQuestion>? = null
) {
    data class TestPageInfo(
        val totalPage: Int,
        val page: Int
    )

    data class TestQuestion(
        val number: Int,
        val title: String,
        val announce: String
    )
}
