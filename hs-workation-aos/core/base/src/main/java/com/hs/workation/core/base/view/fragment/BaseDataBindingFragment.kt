package com.hs.workation.core.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.hs.workation.core.common.R
import com.hs.workation.core.component.CommonDialog
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.util.statusBarHeight

open class BaseDataBindingFragment<T: ViewDataBinding>(@LayoutRes val layoutResId: Int) : Fragment() {

    // Binding
    private var _binding: T? = null
    val binding get() = _binding!!

    // Toast
    private var toast: Toast? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingDataBinding(inflater = inflater, container = container)
        return binding.root
    }

    private fun settingDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    open fun setStatusBarPadding(
        view: View,
        isPadding: Boolean,
        isStatusIconBlack: Boolean
    ) {
        // 상태바 아이콘 색상
        val wic = WindowInsetsControllerCompat(requireActivity().window, requireActivity().window.decorView)
        wic.isAppearanceLightStatusBars = isStatusIconBlack // true: 어두운 아이콘, false: 밝은 아이콘
//        wic.isAppearanceLightNavigationBars = isNaviIconBlack // true: 어두운 아이콘, false: 밝은 아이콘

        // Fragment 배경색에 따라 상태바 배경색 결정
        if(isPadding) {
            view.setPadding(0, requireActivity().statusBarHeight(), 0, 0)
        } else {
            view.setPadding(0, 0, 0, 0)
        }
    }

    open fun showToast(message: String?) {
        CommonToast.makeToast(binding.root, message?:"")
    }

    open fun showNetworkErrorDialog(onClick: (agree: Boolean) -> Unit) {
        CommonDialog(context = requireContext())
            .setDialogCancelable(false)
            .setTitle(getString(R.string.common_alert))
            .setContents(getString(R.string.network_connection_check))
            .setPositiveText(getString(R.string.common_confirm))
            .setTransparentBackground()
            .setClickResultListener(object : CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean) {
                    onClick.invoke(agree)
                }
            })
            .show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}