package com.example.eventbus.greenrobot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.eventbus.R
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ThreeActivity : AppCompatActivity() {
    private val threeViewModel = ThreeViewModel()

    private lateinit var fragmentManager: FragmentManager
    private lateinit var threeFragment: ThreeFragment
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)

        GreenRobotEventBus.registerBus(this, GreenRobotEventBus.getGreenRobotObject)

        setFragment()
        buttonClickListener()
        observerViewModel()
    }

    fun setFragment() {
        fragmentManager = supportFragmentManager

        threeFragment = ThreeFragment()

        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, threeFragment).commitAllowingStateLoss()
    }

    fun buttonClickListener() {
        // Move to SecondActivity.class
        findViewById<TextView>(R.id.tvMoveToTwo).setOnClickListener {
            startActivity(Intent(this, TwoActivity::class.java))
        }
    }

    fun observerViewModel() {
        threeViewModel.str.observe(this) {
            Log.i("MYTAG", "ThreeActivity Thread Name: ${Thread.currentThread().name}")
            findViewById<TextView>(R.id.tvCount).text = "${it}"
            threeFragment.view?.findViewById<TextView>(R.id.tvCount)?.text = "${it}"
            Log.i("MYTAG", "ThreeActivity 관찰자 ${it}")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEventBus(data: String) {
        Log.i("MYTAG", "ThreeActivity-receiveEventBus ${data}")
        threeViewModel.setData(data)
    }

    override fun onDestroy() {
        GreenRobotEventBus.unregisterBus(this, GreenRobotEventBus.getGreenRobotObject)
        super.onDestroy()
    }
}