package com.my.xmlnavigationdemo1.bottomnav.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.xmlnavigationdemo1.R

class BOneFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MYTAG ${javaClass.simpleName}", "onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MYTAG ${javaClass.simpleName}", "onViewCreated()")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_one, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }

}