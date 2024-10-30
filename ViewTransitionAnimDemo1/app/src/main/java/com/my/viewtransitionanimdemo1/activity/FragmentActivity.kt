package com.my.viewtransitionanimdemo1.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.my.viewtransitionanimdemo1.R
import com.my.viewtransitionanimdemo1.databinding.ActivityFragmentBinding
import com.my.viewtransitionanimdemo1.navigate.NavFragment

class FragmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentBinding

    private var _navFragment: NavFragment? = null
    val navFragment: NavFragment? get() = _navFragment
    private var _fragmentNavController: NavController? = null
    val fragmentNavController: NavController? get() = _fragmentNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(_fragmentNavController == null) {
            val fragmentNavFragment = this@FragmentActivity.supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
            _fragmentNavController = fragmentNavFragment.navController
            _fragmentNavController?.setGraph(R.navigation.nav_fragment)
            _navFragment = NavFragment(_fragmentNavController!!)
            Log.d("MYTAG", "Initialized ${navFragment} / ${fragmentNavController}")
        }
    }
}