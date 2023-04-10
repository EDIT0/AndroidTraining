package com.example.bottomnavigation

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.bottomnavigation.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    lateinit var activityMainBinding: ActivityMainBinding

    private var fragmentManager: FragmentManager? = null
    private var oneFragment: OneFragment? = null
    private var twoFragment: TwoFragment? = null
    private var threeFragment: ThreeFragment? = null

    private var toast: Toast? = null

    private lateinit var badgeDrawable: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        fragmentManager = supportFragmentManager

        oneFragment = OneFragment()
        fragmentManager?.beginTransaction()?.replace(activityMainBinding.framelayout.id, oneFragment!!)?.commit()

        activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId){
                    R.id.oneFragment -> {
                        if (oneFragment == null) {
                            oneFragment = OneFragment()
                            fragmentManager!!.beginTransaction().add(activityMainBinding.framelayout.id, oneFragment!!).commit()
                        }

                        if (oneFragment != null) fragmentManager!!.beginTransaction().show(oneFragment!!).commit()
                        if (twoFragment != null) fragmentManager!!.beginTransaction().hide(twoFragment!!).commit()
                        if (threeFragment != null) fragmentManager!!.beginTransaction().hide(threeFragment!!).commit()
                    }
                    R.id.twoFragment -> {
                        if (twoFragment == null) {
                            twoFragment = TwoFragment.newInstance("Welcome", "to TwoFragment")
                            fragmentManager!!.beginTransaction().add(activityMainBinding.framelayout.id, twoFragment!!).commit()
                        }

                        if (oneFragment != null) fragmentManager!!.beginTransaction().hide(oneFragment!!).commit()
                        if (twoFragment != null) fragmentManager!!.beginTransaction().show(twoFragment!!).commit()
                        if (threeFragment != null) fragmentManager!!.beginTransaction().hide(threeFragment!!).commit()
                    }
                    R.id.threeFragment -> {
                        if (threeFragment == null) {
                            threeFragment = ThreeFragment()
                            fragmentManager!!.beginTransaction().add(activityMainBinding.framelayout.id, threeFragment!!).commit()
                        }

                        if (oneFragment != null) fragmentManager!!.beginTransaction().hide(oneFragment!!).commit()
                        if (twoFragment != null) fragmentManager!!.beginTransaction().hide(twoFragment!!).commit()
                        if (threeFragment != null) fragmentManager!!.beginTransaction().show(threeFragment!!).commit()
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
}