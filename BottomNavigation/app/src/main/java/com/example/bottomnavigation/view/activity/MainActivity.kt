package com.example.bottomnavigation.view.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.bottomnavigation.viewmodel.MainViewModel
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.ActivityMainBinding
import com.example.bottomnavigation.event.MainViewModelEvent
import com.example.bottomnavigation.view.fragment.OneFragment
import com.example.bottomnavigation.view.fragment.ThreeFragment
import com.example.bottomnavigation.view.fragment.TwoFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val TAG = "MYTAG"

    lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private var fragmentManager: FragmentManager? = null

    private var toast: Toast? = null

    private lateinit var badgeDrawable: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        fragmentManager = supportFragmentManager // configuration change가 발생해도 add한 Fragment들은 유지
        Log.d(TAG, "fragmentManager : ${fragmentManager.hashCode()}")

        lifecycleScope.launch {
            mainViewModel.bottomNaviUiState
                .collect {
                    when(it.fragment) {
                        is OneFragment -> {
                            if(fragmentManager?.findFragmentByTag("1") == null) {
                                fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, it.fragment, "1")?.commit()
                            }

                            fragmentManager?.findFragmentByTag("1")?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("2")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("3")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }

                            delay(200L)
                            mainViewModel.handleViewModelEvent(MainViewModelEvent.GetCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.oneFragment))
                        }
                        is TwoFragment -> {
                            if(fragmentManager?.findFragmentByTag("2") == null) {
                                fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, it.fragment, "2")?.commit()
                            }

                            fragmentManager?.findFragmentByTag("1")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("2")?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("3")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }

                            delay(200L)
                            mainViewModel.handleViewModelEvent(MainViewModelEvent.GetCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.twoFragment))
                        }
                        is ThreeFragment -> {
                            if(fragmentManager?.findFragmentByTag("3") == null) {
                                fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, it.fragment, "3")?.commit()
                            }

                            fragmentManager?.findFragmentByTag("1")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("2")?.let {
                                fragmentManager?.beginTransaction()?.hide(it)?.commit()
                            }
                            fragmentManager?.findFragmentByTag("3")?.let {
                                fragmentManager?.beginTransaction()?.show(it)?.commit()
                            }

                            delay(200L)
                            mainViewModel.handleViewModelEvent(MainViewModelEvent.GetCurrentBottomNaviScreenId(currentBottomNaviScreenId = R.id.threeFragment))
                        }
                    }
                }
        }

        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.oneFragment -> {
//                        if(fragmentManager?.findFragmentByTag("1") == null) {
//                            val fragment = mainViewModel.getBottomNaviFragment(R.id.oneFragment)
                            mainViewModel.handleViewModelEvent(mainViewModelEvent = MainViewModelEvent.GetBottomNaviFragment(R.id.oneFragment))
//                            fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, fragment, "1")?.commit()
//                            Log.d(TAG, "Add One Fragment in FrameLayout")
//                        }

//                        fragmentManager?.findFragmentByTag("1")?.let {
//                            fragmentManager?.beginTransaction()?.show(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("2")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("3")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }

//                        mainViewModel.setCurrentBottomNaviScreen(R.id.oneFragment)
                    }
                    R.id.twoFragment -> {
//                        if(fragmentManager?.findFragmentByTag("2") == null) {
//                            val fragment = mainViewModel.getBottomNaviFragment(R.id.twoFragment)
                            mainViewModel.handleViewModelEvent(mainViewModelEvent = MainViewModelEvent.GetBottomNaviFragment(R.id.twoFragment))
//                            fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, fragment, "2")?.commit()
//                            Log.d(TAG, "Add Two Fragment in FrameLayout")
//                        }

//                        fragmentManager?.findFragmentByTag("1")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("2")?.let {
//                            fragmentManager?.beginTransaction()?.show(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("3")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }

//                        mainViewModel.setCurrentBottomNaviScreen(R.id.twoFragment)
                    }
                    R.id.threeFragment -> {
//                        if(fragmentManager?.findFragmentByTag("3") == null) {
//                            val fragment = mainViewModel.getBottomNaviFragment(R.id.threeFragment)
                            mainViewModel.handleViewModelEvent(mainViewModelEvent = MainViewModelEvent.GetBottomNaviFragment(R.id.threeFragment))
//                            fragmentManager?.beginTransaction()?.add(activityMainBinding.framelayout.id, fragment, "3")?.commit()
//                            Log.d(TAG, "Add Three Fragment in FrameLayout")
//                        }

//                        fragmentManager?.findFragmentByTag("1")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("2")?.let {
//                            fragmentManager?.beginTransaction()?.hide(it)?.commit()
//                        }
//                        fragmentManager?.findFragmentByTag("3")?.let {
//                            fragmentManager?.beginTransaction()?.show(it)?.commit()
//                        }

//                        mainViewModel.setCurrentBottomNaviScreen(R.id.threeFragment)
                    }
                }

                return true
            }
        })

        activityMainBinding.fab.setOnClickListener {
            showToast("Clicked Fab")
        }

        badgeDrawable = activityMainBinding.bottomNavigation.getOrCreateBadge(R.id.threeFragment)
        badgeDrawable.isVisible = true
        badgeDrawable.number = 10
        badgeDrawable.backgroundColor = Color.parseColor("#5BFFB0")
        badgeDrawable.badgeGravity = BadgeDrawable.TOP_START


    }

    fun showToast(str: String) {
        toast?.cancel()
        toast = Toast.makeText(this, str, Toast.LENGTH_SHORT)
        toast?.show()
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "현재 Fragment : ${mainViewModel.bottomNaviUiState.value.currentBottomNaviScreenId}")
        activityMainBinding.bottomNavigation.selectedItemId = mainViewModel.bottomNaviUiState.value.currentBottomNaviScreenId?:-1
    }
}