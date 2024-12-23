package com.hs.workation.feature.splash.viewmodel

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.event.SplashViewModelEvent
import com.hs.workation.feature.splash.navigation.NavSplash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private var _navSplash: NavSplash? = null
    val navSplash: NavSplash? get() = _navSplash
    private var _splashNavController: NavController? = null
    val splashNavController: NavController? get() = _splashNavController

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    fun handleViewModelEvent(splashViewModelEvent: SplashViewModelEvent) {
        when(splashViewModelEvent) {
            is SplashViewModelEvent.SettingNavigation -> {
                val splashNavFragment = splashViewModelEvent.activity.supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
                _splashNavController = splashNavFragment.navController
                _splashNavController?.setGraph(R.navigation.nav_splash)
                _navSplash = NavSplash(_splashNavController!!)
            }
        }
    }

    enum class SideEffectEvent {
        NETWORK_ERROR
    }
}