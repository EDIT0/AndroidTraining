package com.my.xmlnavigationdemo1.bottomnav.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.my.xmlnavigationdemo1.R
import com.my.xmlnavigationdemo1.databinding.FragmentBTwoBinding
import com.my.xmlnavigationdemo1.databinding.FragmentCOneBinding

class BTwoFragment : Fragment() {

    private var _binding: FragmentBTwoBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MYTAG ${javaClass.simpleName}", "onViewCreated()")

        _binding = FragmentBTwoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        _binding = null

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }

}