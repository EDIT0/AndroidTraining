package com.example.bottomnavigationwithviewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigationwithviewpager.databinding.FragmentOneBinding

class OneFragment : Fragment() {

    private val TAG = OneFragment::class.java.simpleName

    lateinit var fragmentOneBinding: FragmentOneBinding

    private var isFirstStart = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentOneBinding = FragmentOneBinding.bind(inflater.inflate(R.layout.fragment_one, container, false))
        return fragmentOneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "onResume()")

        if(isFirstStart) {
            isFirstStart = false
            fragmentOneBinding.tvMain.text = "OneFragment"
            Log.i(TAG, "Data Load !!")
        }
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "onPause()")
    }

}