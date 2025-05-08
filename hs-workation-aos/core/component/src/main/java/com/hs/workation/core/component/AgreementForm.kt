package com.hs.workation.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hs.workation.core.util.OnSingleClickListener.onSingleClick

/**
 * 약관 동의 폼
 * TODO : 실제 데이터에 따라 구현이 달라질 수도 있음
 * @param modifier : 스타일 필요에 따라 추가
 * @param serviceName : 서비스 명
 * @param agreementList : 약관 동의 리스트
 * @param onAgree : 필수 약관 동의 시 버튼 이벤트
 */
@Composable
fun AgreementForm(
    modifier: Modifier = Modifier,
    serviceName: String,
    agreementList: List<Agreement>,
    onAgree: (List<Agreement>) -> Unit
) {
    // 체크 박스 목록 mutableStateList 로 변환
    val checkBoxItems = remember { agreementList.toMutableStateList() }

    // 전체 선택 상태 저장
    var agreeAll by remember { mutableStateOf(checkBoxItems.all { it.isChecked.value }) }

    val checkedColor = colorResource(com.hs.workation.core.resource.R.color.grey_900)
    val uncheckedColor = colorResource(com.hs.workation.core.resource.R.color.grey_400)
    val transparentColor = colorResource(com.hs.workation.core.resource.R.color.transparent)

    Column(modifier) {
        /** 타이틀 영역 */
        AgreementListItem(
            text = serviceName,
            isTitle = true,
            checked = agreeAll,
            onCheckedChange = {
                agreeAll = it
                checkBoxItems.forEach { item ->
                    item.isChecked.value = it
                }
            },
            checkedColor = checkedColor,
            unCheckedColor = uncheckedColor,
        )

        /** 세부 약관 영역 */
        checkBoxItems.forEach {
            // 필수 선택 여부 스트링
            val isRequiredString = if (it.isRequired) "필수" else "선택"

            AgreementListItem(
                text = "${it.title} ($isRequiredString)",
                isTitle = false,
                checked = it.isChecked.value,
                onCheckedChange = { newValue ->
                    it.isChecked.value = newValue

                    if (!newValue) {
                        // 개별 체크박스 해제 시 전체 선택 체크박스를 해제
                        agreeAll = false
                    } else {
                        // 모든 체크박스가 선택되었을 때 전체 선택 체크박스 활성화
                        agreeAll = checkBoxItems.all { it.isChecked.value }
                    }
                },
                checkedColor = transparentColor,
                unCheckedColor = uncheckedColor,
                checkmarkColor = checkedColor
            )
        }

        /** 버튼 : 필수 약관이 모두 동의인 경우 버튼 활성화 */
        var agreeAllRequired by remember { mutableStateOf(false) }
        val requiredItems = checkBoxItems.filter { it.isRequired }
        agreeAllRequired = requiredItems.all { it.isChecked.value }

        ActiveButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            onButtonClick = {
                onAgree(checkBoxItems.filter { it.isChecked.value })
            },
            isEnable = agreeAllRequired,
            title = "Button Title",
            titleColor = Pair(com.hs.workation.core.resource.R.color.grey_600, com.hs.workation.core.resource.R.color.blue_500),
            titleSize = 16f,
            elevation = 5,
            cornerRadius = 20f,
            backgroundColor = Pair(com.hs.workation.core.resource.R.color.grey_400, com.hs.workation.core.resource.R.color.white),
            borderStroke = 0.5f,
            borderStrokeColor = Pair(com.hs.workation.core.resource.R.color.grey_500, com.hs.workation.core.resource.R.color.teal_200)
        )
    }
}

/**
 * 약관 동의 리스트 아이템
 *
 * @param text : 텍스트
 * @param isTitle : 전체 약관 동의 (제목)
 * @param checked : 체크 상태
 * @param onCheckedChange : 체크 변경 이벤트
 * @param checkedColor : 체크 색상
 * @param unCheckedColor : 언체크 색상
 * @param checkmarkColor : 체크 마크 색상
 */
@Composable
fun AgreementListItem(
    text: String,
    isTitle: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedColor: Color,
    unCheckedColor: Color,
    checkmarkColor: Color? = Color.White
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            colors = CheckboxDefaults.colors(
                uncheckedColor = unCheckedColor,
                checkedColor = checkedColor,
                checkmarkColor = checkmarkColor!!
            ))

        if (isTitle) {
            AgreementTitle(text)
        } else {
            AgreementDetails(text)
        }
    }
}

/**
 * 약관 전체 선택 영역
 *
 * @param text
 */
@Composable
fun AgreementTitle(text: String) {
    Text(
        text = "${text}의 모든 약관에 동의합니다.",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
}

/**
 * 약관 개별 선택 영역
 *
 * @param text
 */
@Composable
fun AgreementDetails(text: String) {
    val borderColor = colorResource(com.hs.workation.core.resource.R.color.grey_400)

    Row(Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = colorResource(com.hs.workation.core.resource.R.color.grey_700),
            modifier = Modifier.weight(.8f)
        )

        Text(
            text = "자세히 보기",
            fontSize = 12.sp,
            color = borderColor,
            modifier = Modifier
                .height(20.dp)
                .onSingleClick {
                    // TODO : 약관 보기
                },
            textDecoration = TextDecoration.Underline
        )
    }
}

/**
 * 목업 데이터 클래스
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

@Preview(showBackground = true)
@Composable
private fun AgreementFormPreview() {
    // 목업 데이터
    val list = listOf(
        Agreement(0, "서비스이용약관1", true),
        Agreement(1, "서비스이용약관2", true),
        Agreement(2, "서비스이용약관3", true),
        Agreement(3, "서비스이용약관4", false),
        Agreement(4, "위치기반서비스이용약관5", false),
    )

    AgreementForm(
        serviceName = "ㅇㅇ서비스",
        agreementList = list
    ) {}
}