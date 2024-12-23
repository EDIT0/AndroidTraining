package com.hs.workation.core.model.mock

/**
 * 공간 정보 목업 데이터 클래스
 *
 * @property name : 이름
 * @property description : 부가 설명
 * @property pricePerUnit : 단위 당 가격
 * @property unit : 단위
 * @property thumbnails : 썸네일 목록
 */
data class SpaceInfo(
    val name: String,
    val description: String,
    val pricePerUnit: Int,
    val unit: String,
    val thumbnails: List<String>
)
