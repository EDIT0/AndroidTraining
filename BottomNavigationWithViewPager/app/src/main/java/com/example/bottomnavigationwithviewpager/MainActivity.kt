package com.example.bottomnavigationwithviewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bottomnavigationwithviewpager.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        mainViewPagerAndBottomNavigationSetting()
    }

    private fun mainViewPagerAndBottomNavigationSetting() {
        activityMainBinding.vpMain.setOffscreenPageLimit(3); //화면 갯수 설정
        val viewPagerAdapter = viewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addItemfragment(OneFragment())
        viewPagerAdapter.addItemfragment(TwoFragment())
        viewPagerAdapter.addItemfragment(ThreeFragment())

        activityMainBinding.vpMain.adapter = viewPagerAdapter


        activityMainBinding.vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                activityMainBinding.bottomNavigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })


        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.oneFragment -> {
                        // ViewPager의 현재 item에 첫 번째 화면을 대입
                        activityMainBinding.vpMain.currentItem = 0
                        return true
                    }
                    R.id.twoFragment -> {
                        // ViewPager의 현재 item에 두 번째 화면을 대입
                        activityMainBinding.vpMain.currentItem = 1
                        return true
                    }
                    R.id.threeFragment -> {
                        // ViewPager의 현재 item에 세 번째 화면을 대입
                        activityMainBinding.vpMain.currentItem = 2
                        return true
                    }
                    else -> {
                        return false
                    }
                }
                true
            }
        })

    }

    inner class viewPagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        var items: ArrayList<Fragment> = ArrayList<Fragment>() //프래그먼트 화면을 담아둘 배열


        //ArrayList에 add로 화면 추가하는 메소드
        fun addItemfragment(item: Fragment) {
            items.add(item)
        }

        override fun getCount(): Int = items.size


        override fun getItem(position: Int): Fragment = items.get(position)
    }
}