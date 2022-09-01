package com.example.coordinatorlayoutdemo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coordinatorlayoutdemo3.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter

    private lateinit var oneFragment: OneFragment
    private lateinit var twoFragment: TwoFragment
    private lateinit var threeFragment: ThreeFragment

    val itemList = listOf<Item>(
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Orange"),
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Mango"),
        Item("Apple"),
        Item("Banana"),
        Item("Guava"),
        Item("Lemon"),
        Item("Pear"),
        Item("Orange")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        adapter = Adapter()

//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter

//        adapter.updateList(itemList)



        initFragment()
        mainViewPagerAndTabLayoutSetting()



    }


    private fun initFragment() {
        oneFragment = OneFragment()
        twoFragment = TwoFragment()
        threeFragment = ThreeFragment()
    }

    private fun mainViewPagerAndTabLayoutSetting() {
        binding.vpMain.setOffscreenPageLimit(3); //화면 갯수 설정
        val viewPagerAdapter = viewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addItemfragment(oneFragment)
        viewPagerAdapter.addItemfragment(twoFragment)
        viewPagerAdapter.addItemfragment(threeFragment)

        binding.vpMain.adapter = viewPagerAdapter

        val tab_mainFragment = binding.tablayout.newTab().setText("1번 탭")
        binding.tablayout.addTab(tab_mainFragment)
        val tab_mainSounddoodleListFragment = binding.tablayout.newTab().setText("2번 탭")
        binding.tablayout.addTab(tab_mainSounddoodleListFragment)
        val tab_mainDoodleListFragment = binding.tablayout.newTab().setText("3번 탭")
        binding.tablayout.addTab(tab_mainDoodleListFragment)

        // 탭 가리기
//        binding.toolbar.getTabAt(0)?.view?.visibility = View.GONE

        binding.vpMain.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tablayout))
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 선택되지 않았던 탭이 선택되었을 경우
                binding.vpMain.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택된 상태에서 선택되지 않음으로 변경될 경우
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 경우
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