package com.example.bottomnavigation.state

import androidx.fragment.app.Fragment
import com.example.bottomnavigation.R
import com.example.bottomnavigation.view.fragment.OneFragment

data class BottomNavigationUiState(
    val fragment: Fragment? = OneFragment(),
    val currentBottomNaviScreenId: Int = R.id.oneFragment
)

data class CenterTextUiState(
    val centerText: String = ""
)