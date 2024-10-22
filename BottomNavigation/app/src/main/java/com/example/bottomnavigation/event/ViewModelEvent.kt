package com.example.bottomnavigation.event


sealed interface MainViewModelEvent {
    class GetBottomNaviFragment(val fragmentId: Int): MainViewModelEvent
    class GetCurrentBottomNaviScreenId(val currentBottomNaviScreenId: Int): MainViewModelEvent
}

sealed interface OneViewModelEvent {
    class ChangeCenterText(val centerText: String): OneViewModelEvent
}