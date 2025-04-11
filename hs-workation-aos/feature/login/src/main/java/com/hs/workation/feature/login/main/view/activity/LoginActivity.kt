package com.hs.workation.feature.login.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.hs.workation.core.base.view.activity.BaseDataBindingActivity
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.login.R
import com.hs.workation.feature.login.databinding.ActivityLoginBinding
import com.hs.workation.feature.login.main.event.LoginViewModelEvent
import com.hs.workation.feature.login.main.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseDataBindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginVM: LoginViewModel by viewModels()

    private val navChangeListener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        LogUtil.i_dev("${controller}/${destination.label}/${arguments}")
    }

    val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStatusNavigationBar(view = binding.root, isNaviIconBlack = true)

        requestViewModelEvent(LoginViewModelEvent.SettingNavigation(this@LoginActivity))
    }

    private fun requestViewModelEvent(loginViewModelEvent: LoginViewModelEvent) {
        when(loginViewModelEvent) {
            is LoginViewModelEvent.SettingNavigation -> {
                loginVM.handleViewModelEvent(loginViewModelEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loginVM.loginNavController?.addOnDestinationChangedListener(navChangeListener)
    }

    override fun onPause() {
        super.onPause()
        loginVM.loginNavController?.removeOnDestinationChangedListener(navChangeListener)
    }
}