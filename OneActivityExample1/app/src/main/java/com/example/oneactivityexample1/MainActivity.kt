package com.example.oneactivityexample1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

//    var aFragment: AFragment? = null
//    var bFragment: BFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        aFragment = AFragment()
//        bFragment = BFragment()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, AFragment.newInstance("메인1", "메인2"))
                //BlankFragment.newInstance("파라미터1","파라미터2")
                .commit()
        }



//        transaction.replace(R.id.fragment, aFragment!!)
//        transaction.addToBackStack(null)
//        transaction.commit()

    }
}