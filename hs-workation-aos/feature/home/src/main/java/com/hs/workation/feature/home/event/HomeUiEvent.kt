package com.hs.workation.feature.home.event

import androidx.fragment.app.Fragment

sealed interface BottomNavigationUiEvent {
    class Init : BottomNavigationUiEvent
    class SuccessGetFragment(val fragment: Fragment) : BottomNavigationUiEvent
    class SuccessSaveCurrentBottomNaviScreenId(val bottomNaviScreenId: Int) : BottomNavigationUiEvent
}