package com.hs.workation.feature.main.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.feature.main.R
import com.hs.workation.feature.main.databinding.FragmentHomeNaviBinding
import com.hs.workation.feature.main.view.viewmodel.HomeNaviViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeNaviFragment : BaseDataBindingFragment<FragmentHomeNaviBinding>(R.layout.fragment_home_navi) {

    private val homeNaviVM: HomeNaviViewModel by viewModels()

    private val activity by lazy {
        requireActivity() as com.hs.workation.feature.main.view.activity.HomeActivity
    }

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragmentId = binding.bottomNaviView.selectedItemId

                if (currentFragmentId != R.id.nav_bottom_main_home) {
                    binding.bottomNaviView.selectedItemId = R.id.nav_bottom_main_home
                } else {
                    activity.finishAfterTransition()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = false, isStatusIconBlack = true)

        settingBottomNavi()
    }

    private fun settingBottomNavi() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeNaviVM.sideEffectEvent.collect {
                when(it) {
                    HomeNaviViewModel.SideEffectEvent.COMPLETE_BOTTOM_NAVI_SETTING -> {
                        binding.bottomNaviView.setupWithNavController(homeNaviVM.bottomNavController!!)
                    }
                }
            }
        }

        /**
         * 바텀 내비게이션 셋팅
         */
        requestViewModelEvent(com.hs.workation.feature.main.event.HomeNaviViewModelEvent.SettingBottomNavigation(this@HomeNaviFragment))
    }

    private fun requestViewModelEvent(homeNaviViewModelEvent: com.hs.workation.feature.main.event.HomeNaviViewModelEvent) {
        when(homeNaviViewModelEvent) {
            is com.hs.workation.feature.main.event.HomeNaviViewModelEvent.SettingBottomNavigation -> {
                homeNaviVM.handleViewModelEvent(homeNaviViewModelEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}