package com.hs.workation.feature.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.main.R
import com.hs.workation.feature.main.event.HomeViewModelEvent
import com.hs.workation.feature.main.navigation.NavHome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    private var _navHome: NavHome? = null
    val navHome: NavHome? get() = _navHome
    private var _homeNavController: NavController? = null
    val homeNavController: NavController? get() = _homeNavController

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

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

    override fun onCleared() {
        super.onCleared()

        _sideEffectEvent.close()
    }
}