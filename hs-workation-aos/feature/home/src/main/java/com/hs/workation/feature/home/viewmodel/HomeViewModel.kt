package com.hs.workation.feature.home.viewmodel

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.home.R
import com.hs.workation.feature.home.event.HomeViewModelEvent
import com.hs.workation.feature.home.navigation.NavHome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private var _navHome: NavHome? = null
    val navHome: NavHome? get() = _navHome
    private var _homeNavController: NavController? = null
    val homeNavController: NavController? get() = _homeNavController

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    fun handleViewModelEvent(homeViewModelEvent: HomeViewModelEvent) {
        when(homeViewModelEvent) {
            is HomeViewModelEvent.SettingNavigation -> {
                val splashNavFragment = homeViewModelEvent.activity.supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
                _homeNavController = splashNavFragment.navController
                _homeNavController?.setGraph(R.navigation.nav_home)
                _navHome = NavHome(_homeNavController!!)
            }
        }
    }

    enum class SideEffectEvent {

    }

}