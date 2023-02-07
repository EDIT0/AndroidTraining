package com.example.bottomnavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bottomnavigation.databinding.FragmentThreeBinding
import com.example.bottomnavigation.databinding.FragmentTwoBinding

class ThreeFragment : Fragment() {

    private val TAG = ThreeFragment::class.java.simpleName

    private var _fragmentThreeBinding: FragmentThreeBinding? = null
    private val fragmentThreeBinding get() = _fragmentThreeBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentThreeBinding = FragmentThreeBinding.inflate(inflater, container, false)
        val view = fragmentThreeBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")

        fragmentThreeBinding.tvThree.setOnClickListener {
            (requireActivity() as MainActivity).showToast("${TAG}")
        }
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentThreeBinding = null
        Log.i(TAG, "onDestroyView()")
    }
}