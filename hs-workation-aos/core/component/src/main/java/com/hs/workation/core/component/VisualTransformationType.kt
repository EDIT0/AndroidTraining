package com.hs.workation.core.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.hs.workation.core.util.LogUtil

const val IDENTITY_LENGTH = 13 // 주민등록번호
const val PHONE_NUMBER_LENGTH = 11 // 휴대폰
const val CARD_LENGTH = 16 // 카드
const val CAR_LICENCE_LENGTH = 12 // 운전면허
const val MIDDLE_HYPHEN = " - "

enum class VisualTransformationType {
    NORMAL, IDENTITY, PHONE_NUMBER, CARD, CAR_LICENCE
}

/**
 * 주민등록번호
 * 000000 - 0000000
 */
class IdentityTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            for (i in text.text.indices) {
                append(text.text[i])
                if (i == 5) {
                    append(MIDDLE_HYPHEN)
                }
            }
            toAnnotatedString()
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = when {
                    offset <= 5 -> offset
                    offset in 6..IDENTITY_LENGTH -> offset + (MIDDLE_HYPHEN.length * 1)
                    else -> annotatedString.length
                }
                if (transformedOffset < 0 || transformedOffset > annotatedString.length) {
                    LogUtil.e_dev("originalToTransformed returned invalid mapping: $offset -> $transformedOffset")
                    transformedOffset = 0
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = when {
                    offset <= 5 -> offset
                    offset in 6..IDENTITY_LENGTH + (MIDDLE_HYPHEN.length * 1) -> offset - (MIDDLE_HYPHEN.length * 1)
                    else -> IDENTITY_LENGTH
                }
                if (originalOffset < 0 || originalOffset > IDENTITY_LENGTH) {
                    LogUtil.e_dev("transformedToOriginal returned invalid mapping: $offset -> $originalOffset")
                    originalOffset = 0
                }
                return originalOffset
            }
        }

        return TransformedText(annotatedString, offsetMapping)
    }
}

/**
 * 휴대전화 번호
 * 폼에 맞게 하이픈 추가
 * 000 - 0000 - 0000
 */
class PhoneNumberTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            for (i in text.text.indices) {
                append(text.text[i])
                if (i == 2 || i == 6) {
                    append(MIDDLE_HYPHEN)
                }
            }
            toAnnotatedString()
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = when {
                    offset <= 2 -> offset
                    offset in 3..6 -> offset + (MIDDLE_HYPHEN.length * 1)
                    offset in 7..PHONE_NUMBER_LENGTH - 1 -> offset + (MIDDLE_HYPHEN.length * 2)
                    else -> annotatedString.length
                }
                if (transformedOffset < 0 || transformedOffset > annotatedString.length) {
                    LogUtil.e_dev("originalToTransformed returned invalid mapping: $offset -> $transformedOffset")
                    transformedOffset = 0
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = when {
                    offset <= 2 -> offset
                    offset in 3..6 + MIDDLE_HYPHEN.length -> offset - (MIDDLE_HYPHEN.length * 1)
                    offset in 7 + (MIDDLE_HYPHEN.length * 1)..PHONE_NUMBER_LENGTH + (MIDDLE_HYPHEN.length * 2) -> offset - (MIDDLE_HYPHEN.length * 2)
                    else -> PHONE_NUMBER_LENGTH
                }
                if (originalOffset < 0 || originalOffset > PHONE_NUMBER_LENGTH) {
                    LogUtil.e_dev("transformedToOriginal returned invalid mapping: $offset -> $originalOffset")
                    originalOffset = 0
                }
                return originalOffset
            }
        }

        return TransformedText(annotatedString, offsetMapping)
    }
}

/**
 * 카드 번호
 * 폼에 맞게 하이픈 추가
 * 0000 - 0000 - 0000 - 0000
 */
class CardTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            for (i in text.text.indices) {
                append(text.text[i])
                if (i == 3 || i == 7 || i == 11) {
                    append(MIDDLE_HYPHEN)
                }
            }
            toAnnotatedString()
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = when {
                    offset <= 3 -> offset
                    offset in 4..7 -> offset + MIDDLE_HYPHEN.length
                    offset in 8..11 -> offset + (MIDDLE_HYPHEN.length * 2)
                    offset in 12..CARD_LENGTH - 1 -> offset + (MIDDLE_HYPHEN.length * 3)
                    else -> annotatedString.length
                }
                if (transformedOffset < 0 || transformedOffset > annotatedString.length) {
                    LogUtil.e_dev("originalToTransformed returned invalid mapping: $offset -> $transformedOffset")
                    transformedOffset = 0
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = when {
                    offset <= 3 -> offset
                    offset in 4..7 + MIDDLE_HYPHEN.length -> offset - MIDDLE_HYPHEN.length
                    offset in 8 + MIDDLE_HYPHEN.length..11 + (MIDDLE_HYPHEN.length * 2) -> offset - (MIDDLE_HYPHEN.length * 2)
                    offset in 12 + (MIDDLE_HYPHEN.length * 2)..CARD_LENGTH + (MIDDLE_HYPHEN.length * 3) -> offset - (MIDDLE_HYPHEN.length * 3)
                    else -> CARD_LENGTH
                }
                if (originalOffset < 0 || originalOffset > CARD_LENGTH) {
                    LogUtil.e_dev("transformedToOriginal returned invalid mapping: $offset -> $originalOffset")
                    originalOffset = 0
                }
                return originalOffset
            }
        }

        return TransformedText(annotatedString, offsetMapping)
    }
}

/**
 * 자동차 면허 번호
 * 폼에 맞게 하이픈 추가
 * 00 - 00 - 000000 - 00
 */
class CarLicenceTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = AnnotatedString.Builder().run {
            for (i in text.text.indices) {
                append(text.text[i])
                if (i == 1 || i == 3 || i == 9) {
                    append(MIDDLE_HYPHEN)
                }
            }
            toAnnotatedString()
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = when {
                    offset <= 1 -> offset
                    offset in 2..3 -> offset + MIDDLE_HYPHEN.length
                    offset in 4..9 -> offset + (MIDDLE_HYPHEN.length * 2)
                    offset in 10..CAR_LICENCE_LENGTH - 1 -> offset + (MIDDLE_HYPHEN.length * 3)
                    else -> annotatedString.length
                }
                if (transformedOffset < 0 || transformedOffset > annotatedString.length) {
                    LogUtil.e_dev("originalToTransformed returned invalid mapping: $offset -> $transformedOffset")
                    transformedOffset = 0
                }
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = when {
                    offset <= 1 -> offset
                    offset in 2..3 + MIDDLE_HYPHEN.length -> offset - MIDDLE_HYPHEN.length
                    offset in 4 + MIDDLE_HYPHEN.length..9 + (MIDDLE_HYPHEN.length * 2) -> offset - (MIDDLE_HYPHEN.length * 2)
                    offset in 10 + (MIDDLE_HYPHEN.length * 2)..CAR_LICENCE_LENGTH + (MIDDLE_HYPHEN.length * 3) -> offset - (MIDDLE_HYPHEN.length * 3)
                    else -> CAR_LICENCE_LENGTH
                }
                if (originalOffset < 0 || originalOffset > CAR_LICENCE_LENGTH) {
                    LogUtil.e_dev("transformedToOriginal returned invalid mapping: $offset -> $originalOffset")
                    originalOffset = 0
                }
                return originalOffset
            }
        }

        return TransformedText(annotatedString, offsetMapping)
    }
}