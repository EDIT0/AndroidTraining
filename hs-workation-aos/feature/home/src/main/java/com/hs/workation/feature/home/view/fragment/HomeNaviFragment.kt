package com.hs.workation.feature.home.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.home.R
import com.hs.workation.feature.home.databinding.FragmentHomeNaviBinding
import com.hs.workation.feature.home.event.HomeNaviViewModelEvent
import com.hs.workation.feature.home.view.activity.HomeActivity
import com.hs.workation.feature.home.view.fragment.menu.AFragment
import com.hs.workation.feature.home.view.fragment.menu.BFragment
import com.hs.workation.feature.home.view.fragment.menu.CFragment
import com.hs.workation.feature.home.viewmodel.HomeNaviViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeNaviFragment : BaseDataBindingFragment<FragmentHomeNaviBinding>(R.layout.fragment_home_navi) {

    private val homeNaviVM: HomeNaviViewModel by viewModels()

    private val activity by lazy {
        requireActivity() as HomeActivity
    }

    private var fragmentManager: FragmentManager? = null

    private val aFragmentTag = AFragment::class.java.simpleName
    private val bFragmentTag = BFragment::class.java.simpleName
    private val cFragmentTag = CFragment::class.java.simpleName

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(homeNaviVM.bottomNaviUiState.value.currentBottomNaviScreenId == R.id.aFragment) {
                    activity.finishAfterTransition()
                } else {
                    binding.bottomNaviView.selectedItemId = R.id.aFragment
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
        fragmentManager = activity.supportFragmentManager // configuration change가 발생해도 add한 Fragment들은 유지

        viewLifecycleOwner.lifecycleScope.launch {
            homeNaviVM.sideEffectEvent.collect {
                binding.bottomNaviView.setupWithNavController(homeNaviVM.bottomNavController!!)
            }
        }

        /**
         * 바텀 내비게이션 셋팅
         */
        requestViewModelEvent(HomeNaviViewModelEvent.SettingBottomNavigation(this@HomeNaviFragment))

        viewLifecycleOwner.lifecycleScope.launch {
            homeNaviVM.bottomNaviUiState
                .collect {
                    /**
                     * Tag가 추가되어있지 않으면 add 후 show or hide
                     */
                    when(it.fragment) {
                        is AFragment -> {
                            if(fragmentManager?.findFragmentByTag(aFragmentTag) == null) {
                                fragmentManager?.beginTransaction()?.add(binding.navBottomFragment.id, it.fragment, aFragmentTag)?.commit()
                            }

                            fragmentManager?.findFragmentByTag(aFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(bFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(cFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }

                            delay(200L)
                            requestViewModelEvent(HomeNaviViewModelEvent.SaveCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.aFragment))
                        }
                        is BFragment -> {
                            if(fragmentManager?.findFragmentByTag(bFragmentTag) == null) {
                                fragmentManager?.beginTransaction()?.add(binding.navBottomFragment.id, it.fragment, bFragmentTag)?.commit()
                            }

                            fragmentManager?.findFragmentByTag(aFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(bFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(cFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }

                            delay(200L)
                            requestViewModelEvent(HomeNaviViewModelEvent.SaveCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.bFragment))
                        }
                        is CFragment -> {
                            if(fragmentManager?.findFragmentByTag(cFragmentTag) == null) {
                                fragmentManager?.beginTransaction()?.add(binding.navBottomFragment.id, it.fragment, cFragmentTag)?.commit()
                            }

                            fragmentManager?.findFragmentByTag(aFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(bFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag(cFragmentTag)?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }

                            delay(200L)
                            requestViewModelEvent(HomeNaviViewModelEvent.SaveCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.cFragment))
                        }
                    }
                }
        }

        /**
         * 하단 내비게이션 탭 변경될 경우, 콜백
         */
        binding.bottomNaviView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.aFragment -> {
                        requestViewModelEvent(HomeNaviViewModelEvent.GetBottomNaviFragment(R.id.aFragment))
                    }
                    R.id.bFragment -> {
                        requestViewModelEvent(HomeNaviViewModelEvent.GetBottomNaviFragment(R.id.bFragment))
                    }
                    R.id.cFragment -> {
                        requestViewModelEvent(HomeNaviViewModelEvent.GetBottomNaviFragment(R.id.cFragment))
                    }
                }

                return true
            }
        })
    }

    private fun requestViewModelEvent(homeNaviViewModelEvent: HomeNaviViewModelEvent) {
        when(homeNaviViewModelEvent) {
            is HomeNaviViewModelEvent.SettingBottomNavigation -> {
                homeNaviVM.handleViewModelEvent(homeNaviViewModelEvent)
            }
            is HomeNaviViewModelEvent.GetBottomNaviFragment -> {
                homeNaviVM.handleViewModelEvent(homeNaviViewModelEvent)
            }
            is HomeNaviViewModelEvent.SaveCurrentBottomNaviScreenId -> {
                homeNaviVM.handleViewModelEvent(homeNaviViewModelEvent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtil.i_dev("현재 Bottom Navigation Fragment : ${homeNaviVM.bottomNaviUiState.value.currentBottomNaviScreenId}")
        binding.bottomNaviView.selectedItemId = homeNaviVM.bottomNaviUiState.value.currentBottomNaviScreenId?:-1
    }
}