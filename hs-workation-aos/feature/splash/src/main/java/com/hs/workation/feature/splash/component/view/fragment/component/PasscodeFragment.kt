package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentPasscodeBinding

class PasscodeFragment : BaseDataBindingFragment<FragmentPasscodeBinding>(R.layout.fragment_passcode) {

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)

        binding.vPasscode.apply {
            setInfoText("새로운 결제 비밀번호 6자리를\n등록해주세요")
            numberCount(6)
            onNumberPressedCallback = { number ->
                LogUtil.d_dev("Number: ${number}")
                if(number.length == 6) {
                    CommonToast.makeToast(binding.vPasscode, "입력 완료 ${number}")
                    findNavController().popBackStack()
                }
            }
            removeNumberCallback = { number ->
                LogUtil.d_dev("Remove number ${number}")
            }
        }
    }
}