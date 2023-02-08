package com.example.fragmentviewmodelexample1.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, AFragment.newInstance("", ""))
            .commit()
    }
}