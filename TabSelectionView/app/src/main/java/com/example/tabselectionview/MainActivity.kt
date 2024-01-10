package com.example.tabselectionview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tabselectionview.databinding.ActivityMainBinding
import com.example.tabselectionview.set.SelectionTabView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectionTabView1.apply {
            setTabLayoutHeight(40)
            setTabLayoutBackground(R.drawable.tab_layout_shape_1)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_1)
            setTabIndicatorColor(R.color.blue)
            setTabTextColor(R.color.white, R.color.black)
            setIsWrapContent(true)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView1 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView1 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off"))
        }

        binding.selectionTabView2.apply {
            setTabLayoutHeight(30)
            setTabLayoutBackground(R.drawable.tab_layout_shape_2)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_2)
            setTabIndicatorColor(R.color.gray)
            setTabTextColor(R.color.yellow, R.color.pink)
            setIsWrapContent(true)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView2 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView2 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off", "OnOff"))
        }

        binding.selectionTabView3.apply {
            setTabLayoutHeight(30)
            setTabLayoutBackground(R.drawable.tab_layout_shape_3)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_3)
            setTabIndicatorColor(R.color.pink)
            setTabTextColor(R.color.blue, R.color.black)
            setTabBackground(R.drawable.tab_background_3)
            setIsWrapContent(false)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView3 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView3 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off", "LooooooongTab"))
        }

        binding.selectionTabView4.apply {
            setTabLayoutHeight(60)
            setTabLayoutBackground(R.drawable.tab_layout_shape_4)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_4)
            setTabIndicatorColor(R.color.pink)
            setTabTextColor(R.color.blue, R.color.black)
            setIsWrapContent(false)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView4 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView4 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off", "on", "off"))
        }

        binding.selectionTabView5.apply {
            setTabLayoutHeight(40)
            setTabLayoutBackground(R.drawable.tab_layout_shape_5)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_5)
            setTabIndicatorColor(R.color.gray)
            setTabTextColor(R.color.yellow, R.color.pink)
            setIsWrapContent(false)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView5 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView5 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off", "OnOff"))
        }

        binding.selectionTabView6.apply {
            setTabLayoutHeight(40)
            setTabLayoutBackground(R.drawable.tab_layout_shape_6)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_6)
            setTabIndicatorColor(R.color.gray)
            setTabTextColor(R.color.yellow, R.color.pink)
            setTabBackground(R.drawable.tab_background_6)
            setIsWrapContent(false)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView6 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView6 ${tabTitle}")
                }
            })
            setList(mutableListOf("On", "Off", "OnOff"))
        }

        binding.selectionTabView7.apply {
            setTabLayoutHeight(30)
            setTabLayoutBackground(R.drawable.tab_layout_shape_7)
            setTabIndicatorShape(R.drawable.tab_indicator_shape_7)
            setTabIndicatorColor(R.color.blue)
            setTabTextColor(R.color.white, R.color.black)
            setIsWrapContent(true)
            onTabChangeListener(object : SelectionTabView.TabChangeListener {
                override fun onTabSelected(tabTitle: String) {
                    Log.d("MYTAG", "onTabSelected() selectionTabView1 ${tabTitle}")
                }

                override fun onTabReselected(tabTitle: String) {
                    Log.d("MYTAG", "onTabReselected() selectionTabView1 ${tabTitle}")
                }
            })
            setList(mutableListOf("거리순", "저가순", "거리저가순"))
        }
    }
}