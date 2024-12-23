package com.hs.workation.core.model.mock

/**
 * 타임라인 목업 데이터 클래스
 *
 * @property title : 제목
 * @property items : 콘텐츠
 */
data class Timeline(
    val title: String,
    val items: List<String>
)
