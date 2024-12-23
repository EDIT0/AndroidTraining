package com.hs.workation.core.model.mock

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * 약관 목업 데이터 클래스
 *
 * @property id : 약관 고유 아이디
 * @property title : 서비스 이용약관, 개인정보약관 등
 * @property isRequired : 필수 동의 여부
 * @property isChecked : 매핑을 통해 만들 프로퍼티
 */
data class Agreement(
    val id: Int,
    val title: String,
    val isRequired: Boolean,
    val isChecked: MutableState<Boolean> = mutableStateOf(false)
)
