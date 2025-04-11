package com.hs.workation.feature.main.event

import com.hs.workation.feature.main.view.fragment.HomeNaviFragment

sealed interface HomeViewModelEvent {
    class SettingNavigation(val activity: com.hs.workation.feature.main.view.activity.HomeActivity):
        HomeViewModelEvent
}

sealed interface HomeNaviViewModelEvent {
    class SettingBottomNavigation(val fragment: HomeNaviFragment): HomeNaviViewModelEvent
}