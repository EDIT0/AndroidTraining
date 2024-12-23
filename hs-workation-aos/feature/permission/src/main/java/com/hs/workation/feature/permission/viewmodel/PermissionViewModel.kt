package com.hs.workation.feature.permission.viewmodel

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.permission.R
import com.hs.workation.feature.permission.event.PermissionViewModelEvent
import com.hs.workation.feature.permission.navigation.NavPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private var _navPermission: NavPermission? = null
    val navPermission: NavPermission? get() = _navPermission
    private var _permissionNavController: NavController? = null
    val permissionNavController: NavController? get() = _permissionNavController

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    fun handleViewModelEvent(permissionViewModelEvent: PermissionViewModelEvent) {
        when(permissionViewModelEvent) {
            is PermissionViewModelEvent.SettingNavigation -> {
                val splashNavFragment = permissionViewModelEvent.activity.supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
                _permissionNavController = splashNavFragment.navController
                _permissionNavController?.setGraph(R.navigation.nav_permission)
                _navPermission = NavPermission(_permissionNavController!!)
            }
        }
    }

    enum class SideEffectEvent {

    }
}