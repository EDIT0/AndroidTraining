package com.example.oneactivityexample1

import android.os.Bundle
import android.util.Log
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

        val fragmentManager = supportFragmentManager

        // 백스택 변화를 감지하는 리스너 등록
        fragmentManager.addOnBackStackChangedListener {
            // 백스택에 변화가 있을 때 수행할 작업
            val currentFragment = fragmentManager.findFragmentById(R.id.fragment)
            Log.d("MYTAG", "CurrentFragment: ${currentFragment}")
//            if (currentFragment is YourFragment) {
                // 현재 화면에 표시된 Fragment가 YourFragment인 경우
                // 원하는 작업 수행
//            }
        }

//        transaction.replace(R.id.fragment, aFragment!!)
//        transaction.addToBackStack(null)
//        transaction.commit()

    }

    override fun onResume() {
        super.onResume()
        Log.i("MYTAG", "${javaClass.simpleName} onResume()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MYTAG", "${javaClass.simpleName} onStop()")
    }

    override fun onBackPressed() {
        Log.i("MYTAG", "${javaClass.simpleName} onBackPressed()")
        super.onBackPressed()
    }
}