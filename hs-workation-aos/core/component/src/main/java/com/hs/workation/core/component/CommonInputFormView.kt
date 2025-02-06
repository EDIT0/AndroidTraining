package com.hs.workation.core.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hs.workation.core.component.databinding.CommonInputFormViewBinding

class CommonInputFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CommonInputFormViewBinding = CommonInputFormViewBinding.inflate(
        LayoutInflater.from(context), this, true)

    fun setTitleText(text: String) {
        binding.tvTitle.text = text
    }

    fun setTitleTextAlpha(alpha: Float) {
        binding.tvTitle.alpha = alpha
    }

    fun getCetView(): CommonEditTextView = binding.cetView
}