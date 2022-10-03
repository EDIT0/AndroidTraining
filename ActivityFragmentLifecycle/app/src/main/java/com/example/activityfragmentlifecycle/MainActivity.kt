package com.example.activityfragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.activityfragmentlifecycle.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        const val LifecycleTag = "LifecycleTag"
    }

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding : ActivityMainBinding

    private lateinit var fm : FragmentManager
    private lateinit var transaction : FragmentTransaction

    private val fragmentBox = listOf<Fragment>(FirstFragment(), SecondFragment(), ThirdFragment())
    private var fragmentNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i(LifecycleTag, "${TAG} onCreate")

        fm = supportFragmentManager
        transaction = fm.beginTransaction()
        transaction.apply {
            replace(R.id.frameLayout, fragmentBox[fragmentNumber])
            commit()
        }
        binding.tvFragmentBackStackCount.text = "Current BackStack Count: " + fm.backStackEntryCount


        binding.btnNextPage.setOnClickListener {
            fragmentNumber++
            if(fragmentNumber == fragmentBox.size) {
                fragmentNumber = 0
            }
            replaceFragment(fragmentBox[fragmentNumber])
//            if(fragmentNumber == 0) {
//                replaceFragment(FirstFragment())
//            } else if(fragmentNumber == 1) {
//                replaceFragment(SecondFragment())
//            } else {
//                replaceFragment(ThirdFragment())
//            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        transaction = fm.beginTransaction()

        transaction.apply {
            addToBackStack(null)
            setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            replace(R.id.frameLayout, fragment)
            commit()
        }

        Handler().postDelayed(Runnable {
            kotlin.run {
                Log.i(TAG, "${fm.fragments} / ${fm.backStackEntryCount}")
                runOnUiThread {
                    binding.tvFragmentBackStackCount.text = "Current BackStack Count: " + fm.backStackEntryCount
                }
            }
        }, 500)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.tvFragmentBackStackCount.text = "Current BackStack Count: " + fm.backStackEntryCount
        Log.i(TAG, "${fm.backStackEntryCount}")
    }

    override fun onStart() {
        Log.i(LifecycleTag, "${TAG} onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i(LifecycleTag, "${TAG} onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i(LifecycleTag, "${TAG} onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(LifecycleTag, "${TAG} onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(LifecycleTag, "${TAG} onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.i(LifecycleTag, "${TAG} onRestart")
        super.onRestart()
    }
}