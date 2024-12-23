package com.hs.workation.feature.home.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.home.R
import com.hs.workation.feature.home.event.BottomNavigationUiEvent
import com.hs.workation.feature.home.event.HomeNaviViewModelEvent
import com.hs.workation.feature.home.state.BottomNavigationUiState
import com.hs.workation.feature.home.view.fragment.menu.AFragment
import com.hs.workation.feature.home.view.fragment.menu.BFragment
import com.hs.workation.feature.home.view.fragment.menu.CFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNaviViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private var _bottomNavController: NavController? = null
    val bottomNavController: NavController? get() = _bottomNavController

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    /**
     * 바텀 내비게이션의 프래그먼트들
     */
    private var aFragment: AFragment? = null
    private var bFragment: BFragment? = null
    private var cFragment: CFragment? = null

    private val _bottomNaviUiState = MutableStateFlow<BottomNavigationUiEvent>(BottomNavigationUiEvent.Init())
    val bottomNaviUiState: StateFlow<BottomNavigationUiState> = _bottomNaviUiState.runningFold(BottomNavigationUiState()) { state, event ->
        when(event) {
            is BottomNavigationUiEvent.Init -> {
                state
            }
            is BottomNavigationUiEvent.SuccessSaveCurrentBottomNaviScreenId -> {
                state.copy(currentBottomNaviScreenId = event.bottomNaviScreenId)
            }
            is BottomNavigationUiEvent.SuccessGetFragment -> {
                state.copy(fragment = event.fragment)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, BottomNavigationUiState())

    fun handleViewModelEvent(homeNaviViewModelEvent: HomeNaviViewModelEvent) {
        when(homeNaviViewModelEvent) {
            is HomeNaviViewModelEvent.SettingBottomNavigation -> {
                val navBottomFragment = homeNaviViewModelEvent.fragment.childFragmentManager.findFragmentById(R.id.navBottomFragment) as NavHostFragment
                _bottomNavController = navBottomFragment.navController
                _bottomNavController?.setGraph(R.navigation.nav_bottom_menu)

                viewModelScope.launch {
                    _sideEffectEvent.emit(SideEffectEvent.COMPLETE_BOTTOM_NAVI_SETTING)
                }
            }
            is HomeNaviViewModelEvent.GetBottomNaviFragment -> {
                /**
                 * 변경된 Bottom Navigation Bar 에 대한 Fragment 를 emit
                 */
                viewModelScope.launch {
                    _bottomNaviUiState.emit(
                        BottomNavigationUiEvent.SuccessGetFragment(
                            fragment = when(homeNaviViewModelEvent.fragmentId) {
                                R.id.aFragment -> {
                                    aFragment?: AFragment()
                                }
                                R.id.bFragment -> {
                                    bFragment?: BFragment()
                                }
                                R.id.cFragment -> {
                                    cFragment?: CFragment()
                                } else -> {
                                    aFragment?: AFragment()
                                }
                            }
                        )
                    )
                }
            }
            is HomeNaviViewModelEvent.SaveCurrentBottomNaviScreenId -> {
                /**
                 * 현재 Fragment id 저장
                 */
                viewModelScope.launch {
                    _bottomNaviUiState.emit(
                        BottomNavigationUiEvent.SuccessSaveCurrentBottomNaviScreenId(bottomNaviScreenId = homeNaviViewModelEvent.currentBottomNaviScreenId)
                    )
                }
            }
        }
    }

    enum class SideEffectEvent {
        COMPLETE_BOTTOM_NAVI_SETTING
    }

}