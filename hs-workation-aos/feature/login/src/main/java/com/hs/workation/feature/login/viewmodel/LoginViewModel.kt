package com.hs.workation.feature.login.viewmodel

import android.app.Application
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.hs.workation.core.base.viewmodel.BaseAndroidViewModel
import com.hs.workation.core.util.NetworkManager
import com.hs.workation.feature.login.R
import com.hs.workation.feature.login.event.LoginViewModelEvent
import com.hs.workation.feature.login.navigation.NavLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    app: Application,
    networkManager: NetworkManager
): BaseAndroidViewModel(app, networkManager) {

    private var _navLogin: NavLogin? = null
    val navLogin: NavLogin? get() = _navLogin
    private var _loginNavController: NavController? = null
    val loginNavController: NavController? get() = _loginNavController

    private val _sideEffectEvent = MutableSharedFlow<SideEffectEvent>()
    val sideEffectEvent = _sideEffectEvent.asSharedFlow()

    fun handleViewModelEvent(loginViewModelEvent: LoginViewModelEvent) {
        when(loginViewModelEvent) {
            is LoginViewModelEvent.SettingNavigation -> {
                val splashNavFragment = loginViewModelEvent.activity.supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
                _loginNavController = splashNavFragment.navController
                _loginNavController?.setGraph(R.navigation.nav_login)
                _navLogin = NavLogin(_loginNavController!!)
            }
        }
    }

    enum class SideEffectEvent {

    }
}