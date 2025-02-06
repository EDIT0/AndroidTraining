package com.hs.workation.core.component

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import com.hs.workation.core.component.databinding.CommonEditTextViewBinding

class CommonEditTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TEXT_INPUT_TYPE_PASSWORD = 0
        const val TEXT_INPUT_TYPE_NORMAL = 1
        const val TEXT_INPUT_TYPE_NO_UPPERCASE = 2
        const val TEXT_INPUT_TYPE_NUMBER = 3
    }

    interface CommonEditTextViewCallback {
        fun onChangedText(changedText: String)
        fun onEnterClicked(currentText: String)
        fun onEndIconClicked()
        fun onCommonButtonClicked()
    }

    private var binding: CommonEditTextViewBinding = CommonEditTextViewBinding.inflate(
        LayoutInflater.from(context), this, true)
    private var commonEditTextViewCallback: CommonEditTextViewCallback? = null
    private var currentText: String = ""
    private var isShowEndIcon = false

    init {
        binding.etCommon.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentText = s.toString()
                commonEditTextViewCallback?.onChangedText(currentText)

                binding.ivEnd.visibility = if(isShowEndIcon && s.toString().isEmpty()) {
                    View.GONE
                } else if(isShowEndIcon && s.toString().isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.etCommon.setOnEditorActionListener(object: OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    commonEditTextViewCallback?.onEnterClicked(currentText)
                    hideKeyboard()
                    return true
                }
                return false
            }
        })
    }

    /**
     * 핀 타입은 EditText를 비활성화 시키고 입력창만 보여지도록 구현
     *
     * @param isPin
     */
    fun setPinInputType(isPin: Boolean, clickCallback: () -> Unit) {
        binding.etCommon.setFocusable(!isPin)
        binding.etCommon.setFocusableInTouchMode(!isPin)
        binding.etCommon.setOnClickListener {
            clickCallback.invoke()
        }
    }

    fun setClickCallbackListener(listener: CommonEditTextViewCallback) {
        commonEditTextViewCallback = listener
    }

    fun setHintText(hintText: String, color: Int) {
        binding.etCommon.setHint(hintText)
        binding.etCommon.setHintTextColor(ContextCompat.getColor(context, color))
    }

    fun setInputText(text: String) {
        binding.etCommon.setText(text)
    }

    fun setTextInputType(type: Int) {
        binding.etCommon.inputType = if(type == TEXT_INPUT_TYPE_PASSWORD) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
        } else if(type == TEXT_INPUT_TYPE_NORMAL) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
        } else if(type == TEXT_INPUT_TYPE_NO_UPPERCASE) {
            InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
        } else if(type == TEXT_INPUT_TYPE_NUMBER){
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
        } else {
            InputType.TYPE_CLASS_TEXT
        }
    }

    fun setTextMaxLength(maxLength: Int) {
        binding.etCommon.filters = arrayOf(
            InputFilter.LengthFilter(maxLength),
        )
    }

    fun setEnable(isEnable: Boolean) {
        binding.etCommon.isEnabled = isEnable
    }

    fun getEditText(): EditText = binding.etCommon

    fun setEndIconShow(isShow: Boolean) {
        isShowEndIcon = isShow

        binding.ivEnd.setOnClickListener {
            commonEditTextViewCallback?.onEndIconClicked()
        }
    }
    fun setEndIcon(icon: Int) {
        binding.ivEnd.setImageResource(icon)
    }

    fun setCommonButtonShow(isShow: Boolean) {
        binding.cvCommonButton.visibility = if(isShow) {
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.cvCommonButton.setOnClickListener {
            commonEditTextViewCallback?.onCommonButtonClicked()
        }
    }
    fun setCommonButtonBackgroundColor(color: Int) {
        binding.cvCommonButton.setCardBackgroundColor(ContextCompat.getColor(context, color))
    }

    fun setCommonButtonText(text: String, color: Int) {
        binding.tvCommonButton.text = text
        binding.tvCommonButton.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setCommonButtonEnable(isEnable: Boolean) {
        binding.tvCommonButton.isEnabled = isEnable
    }

    fun setImeOptionActionNext() {
        binding.etCommon.imeOptions = EditorInfo.IME_ACTION_NEXT
    }

    fun setImeOptionActionDone() {
        binding.etCommon.imeOptions = EditorInfo.IME_ACTION_DONE
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}