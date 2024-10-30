package com.my.viewtransitionanimdemo1.navigate

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

class NavFragment(
    private val navController: NavController
) {
    private fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED
}