package com.hs.workation.feature.splash.view.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonDialog
import com.hs.workation.core.util.FCMToken
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.util.ViewSizeUtil
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.viewmodel.SplashStartViewModel
import com.hs.workation.feature.splash.databinding.FragmentSplashStartBinding
import com.hs.workation.feature.splash.event.RemoteConfigUiErrorEvent
import com.hs.workation.feature.splash.event.SplashStartViewModelEvent
import com.hs.workation.feature.splash.state.RemoteConfigUiState
import com.hs.workation.feature.splash.view.activity.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashStartFragment : BaseDataBindingFragment<FragmentSplashStartBinding>(R.layout.fragment_splash_start) {

    private val splashStartVM: SplashStartViewModel by viewModels()
    private val activity by lazy {
        requireActivity() as SplashActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = false, isStatusIconBlack = true)

        collector(splashStartVM)


        // TODO [전종욱] : 임시코드 지워야함
        FCMToken.initFirebaseCloudMessaging(
            onReceiveToken = { token ->

            },
            onFail = {

            }
        )

        binding.cvCompose.setContent {
            ExampleComposeView(splashStartVM.remoteConfigUiState.collectAsState().value)
        }

        binding.btnToPermission.setOnClickListener {
            /**
             * (Activity or Fragment) To Activity 이동 및 데이터 전달 예시
             */
            val animList: List<androidx.core.util.Pair<View, String>> = listOf(
                androidx.core.util.Pair<View, String>(binding.btnToPermission, "btnToPermission"),
                androidx.core.util.Pair<View, String>(binding.tvTop, "tvTop")
            )
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *animList.toTypedArray())
            val dataBundle = Bundle().apply {
                putString("key1", "value1")
                putInt("key2", 123)
            }
            activity.navActivity.navigateToPermissionActivity(requireContext(), activity.launchActivity, options, dataBundle)
        }

        binding.btnToTest1Fragment.setOnClickListener {
            /**
             * Fragment To Fragment 이동 및 데이터 전달 예시
             */
            val dataString = "Hello from SplashStartFragment"
            val dataInt = 42

            val bundle = Bundle().apply {
                putString("dataString", dataString)
                putInt("dataInt", dataInt)
            }
            findNavController().navigate(R.id.action_splashStartFragment_to_test1Fragment, bundle, navOptions = null, navigatorExtras = null)
        }

        /**
         * Test1Fragment 에서 온 데이터 받음
         */
        setFragmentResultListener("requestKey") { _, bundle ->
            val resultStringData = bundle.getString("resultStringKey") // "Result from Test1Fragment"
            val resultIntData = bundle.getInt("resultIntKey") // "Result from Test1Fragment"
            LogUtil.d_dev("resultData : ${resultStringData} / ${resultIntData}")
        }

        /**
         * 컴포넌트 리스트로 이동
         */
        binding.btnToComponentFragment.setOnClickListener {
            findNavController().navigate(R.id.action_splashStartFragment_to_componentFragment, args = null, navOptions = null, navigatorExtras = null)
        }
    }

    private fun collector(splashStartVM: SplashStartViewModel) {
        /**
         * 공통 이벤트
         */
        viewLifecycleOwner.lifecycleScope.launch {
            splashStartVM.sideEffectEvent.collect {
                when(it) {
                    is SplashStartViewModel.SideEffectEvent.NetworkError -> {
                        showNetworkErrorDialog(
                            onClick = {
                                if(it) {
                                    // Remote Config 데이터 요청
                                    requestViewModelEvent(SplashStartViewModelEvent.RequestRemoteConfig())
                                }
                            }
                        )
                    }
                }
            }
        }
        /**
         * Remote Config 데이터 받기
         */
        viewLifecycleOwner.lifecycleScope.launch {
            splashStartVM.remoteConfigUiState
                .collect {
                    if(it.serverStatus == null && it.appVersion == null) {
                        LogUtil.d_dev("Remote Data Null")
                    } else {
                        LogUtil.d_dev("Remote Data ${it}")
                        if(it.serverStatus?.isMaintenance == true) {
                            showToast("앱 점검 중")
                            maintenanceDialog(it.serverStatus.message?:"점검중입니다.")
                        } else {
                            showToast("앱 시작 ${it.appVersion.toString()}")
                        }
                    }
                }
        }

        /**
         * Remote Config 에러 처리 (필요 시 에러 분기 추가 가능)
         * -> 이쪽으로 응답이 떨어지면 앱 시작 불가
         */
        viewLifecycleOwner.lifecycleScope.launch {
            splashStartVM.remoteConfigUiErrorEvent
                .collect {
                    when(it) {
                        is RemoteConfigUiErrorEvent.ConnectionError -> {
                            LogUtil.e_dev("ConnectionError ${it.code} / ${it.message}")
                            canNotStartAppDialog()
                        }
                        is RemoteConfigUiErrorEvent.DataEmpty -> {
                            LogUtil.e_dev("DataEmpty")
                            canNotStartAppDialog()
                        }
                        is RemoteConfigUiErrorEvent.ExceptionHandle -> {
                            LogUtil.e_dev("ExceptionHandle ${it.throwable.message}")
                            canNotStartAppDialog()
                        }
                        is RemoteConfigUiErrorEvent.Fail -> {
                            LogUtil.e_dev("Fail ${it.code} / ${it.message}")
                            canNotStartAppDialog()
                        }
                        is RemoteConfigUiErrorEvent.Init -> { }
                    }
                }
        }
    }

    /**
     * 점검 안내 다이얼로그
     * 안내 다이얼로그 표시 후 앱 종료
     * @param message
     */
    private fun maintenanceDialog(message: String) {
        CommonDialog(context = requireContext())
            .setDialogCancelable(false)
            .setTitle(getString(com.hs.workation.core.common.R.string.common_running_maintenance))
            .setContents(message)
            .setPositiveText(getString(com.hs.workation.core.common.R.string.common_confirm))
            .setTransparentBackground()
            .setClickResultListener(object : CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean) {
                    if (agree) {
                        requireActivity().finish()
                    } else {

                    }
                }
            })
            .show()

    }

    /**
     * Remote Config 에러 발생 시 앱 시작 불가
     */
    private fun canNotStartAppDialog() {
        CommonDialog(context = requireContext())
            .setDialogCancelable(false)
            .setContents(getString(com.hs.workation.core.common.R.string.common_can_not_start_app))
            .setPositiveText(getString(com.hs.workation.core.common.R.string.common_confirm))
            .setTransparentBackground()
            .setClickResultListener(object : CommonDialog.ClickResultCallback {
                override fun clickResult(agree: Boolean) {
                    if (agree) {
                        (requireActivity() as SplashActivity).finish()
                    } else {

                    }
                }
            })
            .show()

    }

    private fun requestViewModelEvent(splashStartViewModelEvent: SplashStartViewModelEvent) {
        when(splashStartViewModelEvent) {
            is SplashStartViewModelEvent.RequestRemoteConfig -> {
                splashStartVM.handleViewModelEvent(splashStartViewModelEvent)
            }
        }
    }

    // ComposeView에 필요한 상태 넘기기 (ViewModel X)
    @Composable
    fun ExampleComposeView(remoteConfigUiState: RemoteConfigUiState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    text = "Compose View ${remoteConfigUiState.appVersion?.android}",
                    style = TextStyle(fontSize = ViewSizeUtil.dpToSp(dp = 30.dp)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Remote Config 데이터 요청
        requestViewModelEvent(SplashStartViewModelEvent.RequestRemoteConfig())
    }

    @Preview(showBackground = true)
    @Composable
    private fun ExampleComposeViewPreview() {
        ExampleComposeView(RemoteConfigUiState())
    }
}