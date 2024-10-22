package com.example.bottomnavigation.event

import androidx.fragment.app.Fragment

sealed interface BottomNavigationUiEvent {
    class Init : BottomNavigationUiEvent
    class SuccessGetFragment(val fragment: Fragment) : BottomNavigationUiEvent
    class SuccessGetCurrentBottomNaviScreenId(val bottomNaviScreenId: Int) : BottomNavigationUiEvent
}

sealed interface CenterTextUiEvent {
    class Init(): CenterTextUiEvent
    class SuccessChangeCenterText(val centerText: String): CenterTextUiEvent
}