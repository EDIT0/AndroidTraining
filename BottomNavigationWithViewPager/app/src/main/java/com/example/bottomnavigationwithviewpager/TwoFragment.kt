package com.example.bottomnavigationwithviewpager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigationwithviewpager.databinding.FragmentThreeBinding
import com.example.bottomnavigationwithviewpager.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    private val TAG = TwoFragment::class.java.simpleName

    lateinit var fragmentTwoBinding: FragmentTwoBinding

    private var isFirstStart = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentTwoBinding = FragmentTwoBinding.bind(inflater.inflate(R.layout.fragment_two, container, false))
        return fragmentTwoBinding.root
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
            fragmentTwoBinding.tvMain.text = "TwoFragment"
            Log.i(TAG, "Data Load !!")
        }
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "onPause()")
    }
}