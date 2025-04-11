package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.Agreement
import com.hs.workation.core.component.AgreementFoldableForm
import com.hs.workation.core.component.AgreementForm
import com.hs.workation.core.component.AgreementFormSheet
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonAgreementBinding

class CommonAgreementFragment : BaseDataBindingFragment<FragmentCommonAgreementBinding>(R.layout.fragment_common_agreement) {

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

        // 목업 데이터
        val listA = listOf(
            Agreement(0, "서비스이용약관1", true),
            Agreement(1, "서비스이용약관2", true),
            Agreement(2, "서비스이용약관3", true),
            Agreement(3, "서비스이용약관4", false),
            Agreement(4, "위치기반서비스이용약관5", false),
        )
        val listB = listOf(
            Agreement(0, "서비스이용약관1", true),
            Agreement(1, "서비스이용약관2", true),
            Agreement(2, "서비스이용약관3", true),
            Agreement(3, "서비스이용약관4", false),
            Agreement(4, "위치기반서비스 location service policy 이용약관5", false),
        )
        val listC = listOf(
            Agreement(0, "서비스이용약관1", true),
            Agreement(1, "서비스이용약관2", true)
        )
        val services = listOf(Pair("abc 서비스", listB), Pair("abc 서비스 (aaa)", listC))

        binding.cvAgreement.setContent {
            AgreementForm(
                serviceName = "abc 서비스",
                agreementList = listA
            ) { selectedList ->
                LogUtil.i_dev("${selectedList.map { it.title }}")
                CommonToast.makeToast(view, "${selectedList.size}개 항목 동의")
            }
        }

        binding.cvAgreementFoldable.setContent {
            Column {
                services.map { service ->
                    AgreementFoldableForm(serviceName = service.first) {
                        AgreementFormSheet(
                            serviceName = service.first,
                            agreementList = service.second,
                            resultCallback = { selectedList ->
                                LogUtil.i_dev("${selectedList.map { it.title }}")
                                CommonToast.makeToast(view, "${selectedList.size}개 항목 동의")
                            }
                        ).show(requireActivity().supportFragmentManager, "")
                    }
                }
            }
        }
    }
}