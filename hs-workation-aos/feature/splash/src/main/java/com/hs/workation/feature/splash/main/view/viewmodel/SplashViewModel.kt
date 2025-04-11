package com.hs.workation.feature.splash.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.main.event.SplashViewModelEvent
import com.hs.workation.feature.splash.main.navigation.NavSplash
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    private var _navSplash: NavSplash? = null
    val navSplash: NavSplash? get() = _navSplash
    private var _splashNavController: NavController? = null
    val splashNavController: NavController? get() = _splashNavController

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

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

    override fun onCleared() {
        super.onCleared()

        _sideEffectEvent.close()
    }
}