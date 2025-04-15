package com.hs.workation.feature.login.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.feature.login.R
import com.hs.workation.feature.login.databinding.FragmentLoginHomeBinding
import com.hs.workation.feature.login.main.event.LoginHomeViewModelEvent
import com.hs.workation.feature.login.main.view.activity.LoginActivity
import com.hs.workation.feature.login.main.view.viewmodel.LoginHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class LoginHomeFragment : BaseDataBindingFragment<FragmentLoginHomeBinding>(R.layout.fragment_login_home) {

    private val loginHomeVM: LoginHomeViewModel by viewModels()

    private val activity by lazy {
        requireActivity() as LoginActivity
    }

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity.finishAfterTransition()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)

        collector(loginHomeVM)

        viewClickListener()
    }

    private fun collector(loginHomeVM: LoginHomeViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            loginHomeVM.sideEffectEvent.collect {
                when(it) {
                    is SideEffectEvent.NetworkError -> {
                        showToast(it.message)
                    }
                }
            }
        }
    }

    private fun viewClickListener() {
        binding.btnLogin.setOnClickListener {
            requestViewModelEvent(LoginHomeViewModelEvent.RequestLogin())
        }

        binding.btnLogout.setOnClickListener {
            requestViewModelEvent(LoginHomeViewModelEvent.RequestLogout())
        }

        binding.btnToHome.setOnClickListener {
            val animList: List<androidx.core.util.Pair<View, String>> = listOf()
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *animList.toTypedArray())
            activity.navActivity.navigateToHomeActivity(requireContext(), activity.launchActivity, options = null, dataBundle = null)
        }
    }

    private fun requestViewModelEvent(loginHomeViewModelEvent: LoginHomeViewModelEvent) {
        when(loginHomeViewModelEvent) {
            is LoginHomeViewModelEvent.RequestLogin -> {
                loginHomeVM.handleViewModelEvent(loginHomeViewModelEvent)
            }
            is LoginHomeViewModelEvent.RequestLogout -> {
                loginHomeVM.handleViewModelEvent(loginHomeViewModelEvent)
            }
        }
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}