package com.example.fragmentviewmodelexample1.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.fragmentviewmodelexample1.R
import com.example.fragmentviewmodelexample1.databinding.ActivityMainBinding
import com.example.fragmentviewmodelexample1.ui.fragment.AFragment
import com.example.fragmentviewmodelexample1.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainVM = mainViewModel
        binding.lifecycleOwner = this

        fragmentStackListener()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, AFragment.newInstance("", ""))
            .commit()

    }

    private fun fragmentStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val fragmentCount = supportFragmentManager.backStackEntryCount + 1
            if(supportFragmentManager.backStackEntryCount == 0) {
                Toast.makeText(binding.root.context, "첫번째 프래그먼트입니다.", Toast.LENGTH_SHORT).show()
            }
            binding.tvTitle.text = "${fragmentCount}번째 프래그먼트"
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0) {
            AlertDialog.Builder(binding.root.context)
                .setTitle("알림")
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("예") {_, _ ->
                    super.onBackPressed()
                }
                .setNegativeButton("아니오") { _, _ ->

                }
                .show()
        } else {
            super.onBackPressed()
        }
        Log.i(TAG, "onBackPressed() ${supportFragmentManager.backStackEntryCount}")
    }
}