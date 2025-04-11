package com.hs.workation.feature.splash.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.hs.workation.core.base.view.activity.BaseDataBindingActivity
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.main.view.viewmodel.SplashViewModel
import com.hs.workation.feature.splash.databinding.ActivitySplashBinding
import com.hs.workation.feature.splash.main.event.SplashViewModelEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseDataBindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashVM: SplashViewModel by viewModels()

    private val navChangeListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        LogUtil.i_dev("${controller}/${destination.label}/${arguments}")
    }

    /**
     * PermissionActivity 에서 온 데이터 받음
     */
    val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        LogUtil.d_dev("launchActivity ${result.resultCode} / ${result.data}")
        LogUtil.d_dev("${result.data?.getStringExtra("Data")}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusNavigationBar(view = binding.root, isNaviIconBlack = true)

        requestViewModelEvent(SplashViewModelEvent.SettingNavigation(this@SplashActivity))
    }

    private fun requestViewModelEvent(splashViewModelEvent: SplashViewModelEvent) {
        when(splashViewModelEvent) {
            is SplashViewModelEvent.SettingNavigation -> {
                splashVM.handleViewModelEvent(splashViewModelEvent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        splashVM.splashNavController?.addOnDestinationChangedListener(navChangeListener)
    }

    override fun onPause() {
        super.onPause()
        splashVM.splashNavController?.removeOnDestinationChangedListener(navChangeListener)
    }
}