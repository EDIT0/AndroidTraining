package com.hs.workation.core.component.passcodeview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.hs.workation.core.component.databinding.PasscodeViewBinding

/**
 * Passcode View
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
@SuppressLint("SetTextI18n", "ResourceAsColor")
class PasscodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), PasscodeViewContract {

    private var binding: PasscodeViewBinding = PasscodeViewBinding.inflate(LayoutInflater.from(context), this, true)

    // 번호 누를 때 마다 현재까지 입력한 number 콜백
    var onNumberPressedCallback: ((String) -> Unit)? = null
    // 번호 지울 경우, 지운 후 현재 number 콜백
    var removeNumberCallback: ((String) -> Unit)? = null
    // 최대 입력 숫자 수
    private var limitNumberCount = 0
    // 현재 입력된 숫자 수
    private var currentNumberCount = 0
    // 현재 입력된 숫자
    private var currentNumber = ""

    init {
        // 0~9까지 번호 생성 후 섞기
        val numberList = makePasscodeNumber(start = 0, end = 9)
        binding.apply {
            tvNumber1.text = "${numberList[1]}"
            tvNumber2.text = "${numberList[2]}"
            tvNumber3.text = "${numberList[3]}"
            tvNumber4.text = "${numberList[4]}"
            tvNumber5.text = "${numberList[5]}"
            tvNumber6.text = "${numberList[6]}"
            tvNumber7.text = "${numberList[7]}"
            tvNumber8.text = "${numberList[8]}"
            tvNumber9.text = "${numberList[9]}"
            tvNumber0.text = "${numberList[0]}"
        }

        binding.tvNumber1.setOnClickListener {
            onPressedNumberListener(binding.tvNumber1.text.toString())
        }
        binding.tvNumber2.setOnClickListener {
            onPressedNumberListener(binding.tvNumber2.text.toString())
        }
        binding.tvNumber3.setOnClickListener {
            onPressedNumberListener(binding.tvNumber3.text.toString())
        }
        binding.tvNumber4.setOnClickListener {
            onPressedNumberListener(binding.tvNumber4.text.toString())
        }
        binding.tvNumber5.setOnClickListener {
            onPressedNumberListener(binding.tvNumber5.text.toString())
        }
        binding.tvNumber6.setOnClickListener {
            onPressedNumberListener(binding.tvNumber6.text.toString())
        }
        binding.tvNumber7.setOnClickListener {
            onPressedNumberListener(binding.tvNumber7.text.toString())
        }
        binding.tvNumber8.setOnClickListener {
            onPressedNumberListener(binding.tvNumber8.text.toString())
        }
        binding.tvNumber9.setOnClickListener {
            onPressedNumberListener(binding.tvNumber9.text.toString())
        }
        binding.tvNumber0.setOnClickListener {
            onPressedNumberListener(binding.tvNumber0.text.toString())
        }
        binding.tvBackText.setOnClickListener {
            onRemoveNumberListener()
        }
    }

    /**
     * 입력하여야 하는 숫자에 대한 안내 텍스트
     *
     * @param text 숫자 입력 정보 텍스트
     */
    override fun setInfoText(text: String) {
        binding.tvInfoText.text = text
    }

    /**
     * 입력 가능한 최대치 설정
     *
     * @param count
     */
    override fun numberCount(count: Int) {
        binding.vPasscodeIndicator.setCircleCount(count)
        limitNumberCount = count
    }

    /**
     * 숫자를 눌렀을 경우, 현재까지 누른 숫자를 onNumberPressedCallback 통하여 콜백
     * 숫자의 길이에 맞게 인디케이터 수정
     *
     * @param number
     */
    override fun onPressedNumberListener(number: String) {
        if(limitNumberCount > currentNumber.length) {
            currentNumber += number
        } else {
            return
        }
        onNumberPressedCallback?.invoke(currentNumber)
        currentNumberCount += 1
        binding.vPasscodeIndicator.setFilledCount(currentNumberCount)

    }

    /**
     * 현재 숫자에서 가장 마지막 숫자를 제거하고, removeNumberCallback 통하여 콜백
     * 숫자의 길이에 맞게 인디케이터 수정
     *
     * @param number
     */
    override fun onRemoveNumberListener() {
        if(currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.substring(0, currentNumber.length - 1)
        } else {
            return
        }
        removeNumberCallback?.invoke(currentNumber)
        currentNumberCount -= 1
        binding.vPasscodeIndicator.setFilledCount(currentNumberCount)

    }

    /**
     * 숫자 섞는 함수
     *
     * @param start 시작 숫자
     * @param end 끝 숫자
     * @return
     */
    private fun makePasscodeNumber(start: Int, end: Int) : List<Int> {
        val numberList = mutableListOf<Int>()
        for(i in start..end) {
            numberList.add(i)
        }
        return numberList.shuffled()
    }
}