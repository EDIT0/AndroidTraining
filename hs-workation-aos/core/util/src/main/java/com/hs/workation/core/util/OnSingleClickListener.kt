package com.hs.workation.core.util

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.databinding.BindingAdapter

object OnSingleClickListener {
    private var isClicked = false

    // 데이터 바인딩에서 사용
    @JvmStatic
    @BindingAdapter(
        value = ["bind:onSingleClick"]
    )
    fun onSingleClick(
        view: View,
        onClickListener: View.OnClickListener
    ) {
        view.setOnClickListener { v ->
            if (isClicked.not()) {
                isClicked = true
                v?.run {
                    postDelayed({
                        isClicked = false
                    }, 350L)
                    onClickListener.onClick(v)
                }
            }
        }
    }

    // 일반 뷰에서 사용
    fun View.onSingleClick(interval: Long = 350L, action: (v: View) -> Unit) {
        setOnClickListener { v ->
            if (isClicked.not()) {
                isClicked = true
                v?.run {
                    postDelayed({
                        isClicked = false
                    }, interval)
                    action(v)
                }
            }
        }
    }

    // 컴포즈에서 사용
    @SuppressLint("ModifierFactoryUnreferencedReceiver")
    fun Modifier.onSingleClick(
        enabled: Boolean = true,
        onClickLabel: String? = null,
        role: Role? = null,
        onClick: () -> Unit
    ) = composed(
        inspectorInfo = debugInspectorInfo {
            name = "clickable"
            properties["enabled"] = enabled
            properties["onClickLabel"] = onClickLabel
            properties["role"] = role
            properties["onClick"] = onClick
        }
    ) {
        val multipleEventsCutter = remember { MultipleEventsCutter.get() }
        Modifier.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onClick = { multipleEventsCutter.processEvent { onClick() } },
            role = role,
            indication = LocalIndication.current,
            interactionSource = remember { MutableInteractionSource() }
        )
    }

    /**
     *  컴포즈 전용 인터페이스
     *  [코드출처] : https://al-e-shevelev.medium.com/how-to-prevent-multiple-clicks-in-android-jetpack-compose-8e62224c9c5e
     **/
    internal interface MultipleEventsCutter {
        fun processEvent(event: () -> Unit)

        companion object
    }

    internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
        MultipleEventsCutterImpl()

    private class MultipleEventsCutterImpl : MultipleEventsCutter {
        private val now: Long
            get() = System.currentTimeMillis()

        private var lastEventTimeMs: Long = 0

        override fun processEvent(event: () -> Unit) {
            if (now - lastEventTimeMs >= 350L) {
                event.invoke()
            }
            lastEventTimeMs = now
        }
    }
}