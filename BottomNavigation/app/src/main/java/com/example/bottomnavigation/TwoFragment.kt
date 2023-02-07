package com.example.bottomnavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bottomnavigation.databinding.FragmentOneBinding
import com.example.bottomnavigation.databinding.FragmentThreeBinding
import com.example.bottomnavigation.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    private val TAG = TwoFragment::class.java.simpleName

    private var _fragmentTwoBinding: FragmentTwoBinding? = null
    private val fragmentTwoBinding get() = _fragmentTwoBinding!!

    var param1 = ""
    var param2 = ""

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    Log.i(TAG, "newInstance")
                    putString("Two1", param1)
                    putString("Two2", param2)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate()")
        arguments?.let {
            param1 = it.getString("Two1").toString()
            param2 = it.getString("Two2").toString()
        }
        Log.i(TAG, "param1: ${param1} / param2: ${param2}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentTwoBinding = FragmentTwoBinding.inflate(inflater, container, false)
        val view = fragmentTwoBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")

        fragmentTwoBinding.tvTwo.setOnClickListener {
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
        _fragmentTwoBinding = null
        Log.i(TAG, "onDestroyView()")
    }
}