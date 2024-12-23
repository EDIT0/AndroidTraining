package com.hs.workation.feature.permission.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.hs.workation.core.base.view.activity.BaseDataBindingActivity
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.permission.R
import com.hs.workation.feature.permission.databinding.ActivityPermissionBinding
import com.hs.workation.feature.permission.event.PermissionViewModelEvent
import com.hs.workation.feature.permission.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionActivity: BaseDataBindingActivity<ActivityPermissionBinding>(R.layout.activity_permission) {

    private val permissionVM: PermissionViewModel by viewModels()

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

        requestViewModelEvent(PermissionViewModelEvent.SettingNavigation(this@PermissionActivity))
    }

    private fun requestViewModelEvent(permissionViewModelEvent: PermissionViewModelEvent) {
        when(permissionViewModelEvent) {
            is PermissionViewModelEvent.SettingNavigation -> {
                permissionVM.handleViewModelEvent(permissionViewModelEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        permissionVM.permissionNavController?.addOnDestinationChangedListener(navChangeListener)
    }

    override fun onPause() {
        super.onPause()
        permissionVM.permissionNavController?.removeOnDestinationChangedListener(navChangeListener)
    }
}