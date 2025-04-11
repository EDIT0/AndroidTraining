package com.hs.workation.feature.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.hs.workation.core.base.view.activity.BaseDataBindingActivity
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.main.R
import com.hs.workation.feature.main.event.HomeViewModelEvent
import com.hs.workation.feature.main.view.viewmodel.HomeViewModel
import com.hs.workation.feature.main.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseDataBindingActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeVM: HomeViewModel by viewModels()

    private val navChangeListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        LogUtil.i_dev("${controller}/${destination.label}/${arguments}")
    }

    val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusNavigationBar(view = binding.root, isNaviIconBlack = true)

        requestViewModelEvent(HomeViewModelEvent.SettingNavigation(this@HomeActivity))
    }

    private fun requestViewModelEvent(homeViewModelEvent: HomeViewModelEvent) {
        when(homeViewModelEvent) {
            is HomeViewModelEvent.SettingNavigation -> {
                homeVM.handleViewModelEvent(homeViewModelEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        homeVM.homeNavController?.addOnDestinationChangedListener(navChangeListener)
    }

    override fun onPause() {
        super.onPause()
        homeVM.homeNavController?.removeOnDestinationChangedListener(navChangeListener)
    }
}