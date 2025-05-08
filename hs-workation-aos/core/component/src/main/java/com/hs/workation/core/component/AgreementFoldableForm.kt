package com.hs.workation.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 아코디언 약관 동의 폼
 *
 * @param showAgreementSheet : 약관 시트 show 이벤트
 */
@Composable
fun AgreementFoldableForm(
    serviceName: String,
    showAgreementSheet: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val icon = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    Surface(
        shape = ShapeDefaults.Medium,
        color = Color.White,
        border = BorderStroke(1.dp, colorResource(com.hs.workation.core.resource.R.color.grey_400)),
        modifier = Modifier.animateContentSize().padding(vertical = 5.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp, 12.dp)) {
            // 제목 영역
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = serviceName,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )

                IconButton(onClick = {
                    isExpanded = !isExpanded
                }) {
                    Icon(icon, "icon")
                }
            }

            // 상세 영역 애니메이션 적용
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Text("$serviceName 제공을 위한 ${serviceName}의 약관에 동의가 필요합니다.",
                        color = colorResource(com.hs.workation.core.resource.R.color.grey_600),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Row(Modifier.fillMaxWidth(), Arrangement.End) {
                        Button(
                            onClick = {
                                showAgreementSheet()
                            },
                            shape = ShapeDefaults.ExtraSmall,
                            contentPadding = PaddingValues(14.dp, 0.dp),
                            modifier = Modifier.padding(top = 5.dp, end = 10.dp)
                        ) {
                            Text("약관 동의하기", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementFoldableFormPreview() {
    AgreementFoldableForm("aa Service") {}
}