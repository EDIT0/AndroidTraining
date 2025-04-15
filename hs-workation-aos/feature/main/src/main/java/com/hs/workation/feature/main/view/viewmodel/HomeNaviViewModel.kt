package com.hs.workation.feature.main.view.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.main.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNaviViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private val scope = viewModelScope

    private var _bottomNavController: NavController? = null
    val bottomNavController: NavController? get() = _bottomNavController

    private val _sideEffectEvent = Channel<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.receiveAsFlow()

    fun handleViewModelEvent(homeNaviViewModelEvent: com.hs.workation.feature.main.event.HomeNaviViewModelEvent) {
        when(homeNaviViewModelEvent) {
            is com.hs.workation.feature.main.event.HomeNaviViewModelEvent.SettingBottomNavigation -> {
                val navBottomFragment = homeNaviViewModelEvent.fragment.childFragmentManager.findFragmentById(R.id.navBottomFragment) as NavHostFragment
                _bottomNavController = navBottomFragment.navController
                _bottomNavController?.setGraph(R.navigation.nav_bottom)

                scope.launch {
                    _sideEffectEvent.send(SideEffectVMEvent.CompleteBottomNaviSetting())
                }
            }
        }
    }

    sealed interface SideEffectVMEvent : SideEffectEvent {
        class CompleteBottomNaviSetting(): SideEffectVMEvent
    }

    override fun onCleared() {
        super.onCleared()

        _sideEffectEvent.close()
    }
}