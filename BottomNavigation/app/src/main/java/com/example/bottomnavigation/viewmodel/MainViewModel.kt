package com.example.bottomnavigation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigation.R
import com.example.bottomnavigation.event.BottomNavigationUiEvent
import com.example.bottomnavigation.event.MainViewModelEvent
import com.example.bottomnavigation.state.BottomNavigationUiState
import com.example.bottomnavigation.view.fragment.OneFragment
import com.example.bottomnavigation.view.fragment.ThreeFragment
import com.example.bottomnavigation.view.fragment.TwoFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var oneFragment: OneFragment? = null
    private var twoFragment: TwoFragment? = null
    private var threeFragment: ThreeFragment? = null

    private val _bottomNaviUiState = MutableStateFlow<BottomNavigationUiEvent>(BottomNavigationUiEvent.Init())
    val bottomNaviUiState: StateFlow<BottomNavigationUiState> = _bottomNaviUiState.runningFold(BottomNavigationUiState()) { state, event ->
        when(event) {
            is BottomNavigationUiEvent.Init -> {
                state
            }
            is BottomNavigationUiEvent.SuccessGetCurrentBottomNaviScreenId -> {
                state.copy(currentBottomNaviScreenId = event.bottomNaviScreenId)
            }
            is BottomNavigationUiEvent.SuccessGetFragment -> {
                state.copy(fragment = event.fragment)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, BottomNavigationUiState())

    fun handleViewModelEvent(mainViewModelEvent: MainViewModelEvent) {
        when(mainViewModelEvent) {
            is MainViewModelEvent.GetBottomNaviFragment -> {
                viewModelScope.launch {
                    _bottomNaviUiState.emit(
                        BottomNavigationUiEvent.SuccessGetFragment(
                            fragment = when(mainViewModelEvent.fragmentId) {
                                R.id.oneFragment -> {
                                    oneFragment?: OneFragment()
                                }
                                R.id.twoFragment -> {
                                    twoFragment?: TwoFragment()
                                }
                                R.id.threeFragment -> {
                                    threeFragment?: ThreeFragment()
                                } else -> {
                                    oneFragment?: OneFragment()
                                }
                            }
                        )
                    )
                }
            }
            is MainViewModelEvent.GetCurrentBottomNaviScreenId -> {
                viewModelScope.launch {
                    _bottomNaviUiState.emit(
                        BottomNavigationUiEvent.SuccessGetCurrentBottomNaviScreenId(bottomNaviScreenId = mainViewModelEvent.currentBottomNaviScreenId)
                    )
                }
            }
        }
    }

//    private var _currentBottomNaviScreen = MutableStateFlow<Int>(R.id.oneFragment)
//    val currentBottomNaviScreen: StateFlow<Int> = _currentBottomNaviScreen.asStateFlow()

//    private var oneFragment: OneFragment? = null
//    private var twoFragment: TwoFragment? = null
//    private var threeFragment: ThreeFragment? = null

//    fun getBottomNaviFragment(fragmentId: Int) : Fragment {
//        return when(fragmentId) {
//            R.id.oneFragment -> {
//                if(oneFragment == null) {
//                    oneFragment = OneFragment()
//                }
//                oneFragment?: OneFragment()
//            }
//            R.id.twoFragment -> {
//                if(twoFragment == null) {
//                    twoFragment = TwoFragment()
//                }
//                twoFragment?: TwoFragment()
//            }
//            R.id.threeFragment -> {
//                if(threeFragment == null) {
//                    threeFragment = ThreeFragment()
//                }
//                threeFragment?: ThreeFragment()
//            } else -> {
//                oneFragment?: OneFragment()
//            }
//        }
//    }

//    fun setCurrentBottomNaviScreen(fragmentId: Int) {
//        _currentBottomNaviScreen.value = fragmentId
//    }
}