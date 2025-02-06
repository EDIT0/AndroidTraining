package com.hs.workation.feature.splash.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonEditTextView
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonInputFormViewBinding

class CommonInputFormViewFragment : BaseDataBindingFragment<FragmentCommonInputFormViewBinding>(R.layout.fragment_common_input_form_view) {
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


        binding.cifv.setTitleText("Hello Title")

        binding.cifv.getCetView()
            .setClickCallbackListener(object : CommonEditTextView.CommonEditTextViewCallback {
                override fun onChangedText(changedText: String) {

                }

                override fun onEnterClicked(currentText: String) {

                }

                override fun onEndIconClicked() {

                }

                override fun onCommonButtonClicked() {

                }

            })
        binding.cifv.getCetView().setTextInputType(CommonEditTextView.TEXT_INPUT_TYPE_PASSWORD)
        binding.cifv.getCetView().setEndIconShow(true)

        binding.cifv.getCetView().setEndIcon(com.hs.workation.core.component.R.drawable.ic_remove_text);
        binding.cifv.getCetView().setCommonButtonShow(true)

        binding.cifv.getCetView().setCommonButtonBackgroundColor(com.hs.workation.core.common.R.color.transparent);
        binding.cifv.getCetView().setCommonButtonText("파일선택", com.hs.workation.core.common.R.color.blue_500);
        binding.cifv.getCetView().setHintText("Hint Text", com.hs.workation.core.common.R.color.grey_300)
//        binding.cifv.getCetView().setPinInputType(true) {
//            if (activity == null) {
//                return@setPinInputType
//            }
//        }
    }
}