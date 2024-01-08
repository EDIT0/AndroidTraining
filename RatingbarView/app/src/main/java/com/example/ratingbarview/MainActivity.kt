package com.example.ratingbarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment: Fragment = RatingbarViewExampleFragment()
        val receiveData = intent.extras
        if (receiveData != null) {
            fragment.arguments = receiveData
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
//            ft.addToBackStack(fragment.fragmentName)
        ft.commitAllowingStateLoss()
    }
}