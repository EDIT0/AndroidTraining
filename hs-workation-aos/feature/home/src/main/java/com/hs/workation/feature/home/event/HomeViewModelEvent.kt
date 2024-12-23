package com.hs.workation.feature.home.event

import com.hs.workation.feature.home.view.activity.HomeActivity
import com.hs.workation.feature.home.view.fragment.HomeNaviFragment

sealed interface HomeViewModelEvent {
    class SettingNavigation(val activity: HomeActivity): HomeViewModelEvent
}

sealed interface HomeNaviViewModelEvent {
    class SettingBottomNavigation(val fragment: HomeNaviFragment): HomeNaviViewModelEvent
    class GetBottomNaviFragment(val fragmentId: Int): HomeNaviViewModelEvent
    class SaveCurrentBottomNaviScreenId(val currentBottomNaviScreenId: Int): HomeNaviViewModelEvent
}