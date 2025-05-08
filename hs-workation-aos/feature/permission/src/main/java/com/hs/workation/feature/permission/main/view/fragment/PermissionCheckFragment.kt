package com.hs.workation.feature.permission.main.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonDialog
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.permission.R
import com.hs.workation.feature.permission.databinding.FragmentPermissionCheckBinding
import com.hs.workation.feature.permission.main.event.PermissionCheckViewModelEvent
import com.hs.workation.feature.permission.main.event.PermissionStateUiErrorEvent
import com.hs.workation.feature.permission.main.view.activity.PermissionActivity
import com.hs.workation.feature.permission.main.view.viewmodel.PermissionCheckViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PermissionCheckFragment : BaseDataBindingFragment<FragmentPermissionCheckBinding>(R.layout.fragment_permission_check) {

    private val permissionCheckVM: PermissionCheckViewModel by viewModels()

    private val activity by lazy {
        requireActivity() as PermissionActivity
    }

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // TODO [전종욱] : 임시 코드 나중에 삭제
                val intent = Intent()
                intent.putExtra("Data", "KO, US, UK")
                activity.setResult(0, intent)
//                activity.finish()
                finishAfterTransition(activity)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)

        val dataBundle = activity.intent.extras
        if (dataBundle != null) {
            val stringValue = dataBundle.getString("key1")
            val intValue = dataBundle.getInt("key2")

            LogUtil.d_dev("String value: $stringValue")
            LogUtil.d_dev("Int value: $intValue")
        }

        collector(permissionCheckVM)
        viewClickListener()

        permissionCheckVM.handleViewModelEvent(PermissionCheckViewModelEvent.InitPermissionManager(activity))

    }

    private fun collector(permissionCheckViewModel: PermissionCheckViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            permissionCheckViewModel.sideEffectEvent.collect {
                when(it) {
                    is SideEffectEvent.NetworkError -> {
                        showNetworkErrorDialog(
                            onClick = {
                                if(it) {

                                }
                            }
                        )
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            permissionCheckViewModel.permissionStateUiState.collect {
                LogUtil.d_dev("${it}")
                if(it.isState == null || it.isRetryAvailable == null) return@collect

                if(it.isState == true) {
                    // 권한 모두 승인 후 로직
                    val animList: List<androidx.core.util.Pair<View, String>> = listOf()
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *animList.toTypedArray())
                    activity.navActivity.navigateToLoginActivity(requireContext(), activity.launchActivity, options = null, dataBundle = null)
                    activity.finish()
                } else if(it.isState == false && it.isRetryAvailable == true) {
                    val sb = StringBuilder()
                    for(rp in it.rejectedPermissions) {
                        sb.append("\n[${rp}]")
                    }
                    CommonDialog(context = requireContext())
                        .setDialogCancelable(false)
                        .setTitle(getString(com.hs.workation.core.resource.R.string.common_alert))
                        .setContents(getString(com.hs.workation.core.resource.R.string.permission_request_essential_permission_message) + "${sb}")
                        .setPositiveText(getString(com.hs.workation.core.resource.R.string.common_confirm))
                        .setTransparentBackground()
                        .setClickResultListener(object : CommonDialog.ClickResultCallback {
                            override fun clickResult(agree: Boolean) {
                                permissionCheckViewModel.handleViewModelEvent(
                                    PermissionCheckViewModelEvent.RequestPermission())
                            }
                        })
                        .show()
                } else {
                    CommonDialog(context = requireContext())
                        .setDialogCancelable(false)
                        .setTitle(getString(com.hs.workation.core.resource.R.string.common_alert))
                        .setContents(getString(com.hs.workation.core.resource.R.string.permission_navigate_to_setting_page_message))
                        .setPositiveText(getString(com.hs.workation.core.resource.R.string.common_confirm))
                        .setTransparentBackground()
                        .setClickResultListener(object : CommonDialog.ClickResultCallback {
                            override fun clickResult(agree: Boolean) {
                                permissionCheckViewModel.handleViewModelEvent(
                                    PermissionCheckViewModelEvent.NavigateToPermissionSettingPage())
                            }
                        })
                        .show()
                }
            }
        }

        /**
         * 에러 처리 (필요 시 에러 분기 추가 가능)
         */
        viewLifecycleOwner.lifecycleScope.launch {
            permissionCheckViewModel.permissionStateUiErrorEvent.collect {
                when(it) {
                    is PermissionStateUiErrorEvent.ConnectionError -> {
                        LogUtil.e_dev("ConnectionError ${it.code} / ${it.message}")
                    }
                    is PermissionStateUiErrorEvent.DataEmpty -> {
                        LogUtil.e_dev("DataEmpty")
                    }
                    is PermissionStateUiErrorEvent.ExceptionHandle -> {
                        LogUtil.e_dev("ExceptionHandle ${it.throwable.message}")
                    }
                    is PermissionStateUiErrorEvent.Fail -> {
                        LogUtil.e_dev("Fail ${it.code} / ${it.message}")
                    }
                    is PermissionStateUiErrorEvent.Init -> { }
                }
            }
        }

    }

    private fun viewClickListener() {
        binding.btnPermissionCheck.setOnClickListener {
            permissionCheckVM.handleViewModelEvent(PermissionCheckViewModelEvent.RequestPermission())
        }
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}