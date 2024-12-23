package com.hs.workation.feature.home.state

import androidx.fragment.app.Fragment
import com.hs.workation.feature.home.R
import com.hs.workation.feature.home.view.fragment.menu.AFragment

data class BottomNavigationUiState(
    val fragment: Fragment? = AFragment(),
    val currentBottomNaviScreenId: Int = R.id.aFragment
)