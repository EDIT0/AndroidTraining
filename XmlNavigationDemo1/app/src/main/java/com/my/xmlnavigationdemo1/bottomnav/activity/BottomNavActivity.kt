package com.my.xmlnavigationdemo1.bottomnav.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.my.xmlnavigationdemo1.R
import com.my.xmlnavigationdemo1.databinding.ActivityBottomNavBinding

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.i("MYTAG ${javaClass.simpleName}", "onCreate()")

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fcv
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = binding.bnv
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.one, R.id.two, R.id.three)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.bnv.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.one -> {
//                    setUpNavigation(R.navigation.bottom_nav_one)
//                    true
//                }
//                R.id.two -> {
//                    setUpNavigation(R.navigation.bottom_nav_two)
//                    true
//                }
//                else -> false
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    /**
     * NavController에 네비게이션 그래프 설정
     *
     * @param navGraphId
     */
    private fun setUpNavigation(navGraphId: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val navGraph = inflater.inflate(navGraphId)
        navHostFragment.navController.graph = navGraph
    }

    override fun onStart() {
        super.onStart()

        Log.i("MYTAG ${javaClass.simpleName}", "onStart()")
    }

    override fun onResume() {
        super.onResume()

        Log.i("MYTAG ${javaClass.simpleName}", "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.i("MYTAG ${javaClass.simpleName}", "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Log.i("MYTAG ${javaClass.simpleName}", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }
}