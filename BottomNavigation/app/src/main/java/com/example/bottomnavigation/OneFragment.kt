package com.example.bottomnavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bottomnavigation.databinding.FragmentOneBinding

class OneFragment : Fragment() {

    private val TAG = OneFragment::class.java.simpleName

    private var _fragmentOneBinding: FragmentOneBinding? = null
    private val fragmentOneBinding get() = _fragmentOneBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentOneBinding = FragmentOneBinding.inflate(inflater, container, false)
        val view = fragmentOneBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")

        fragmentOneBinding.tvOne.setOnClickListener {
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
        _fragmentOneBinding = null
        Log.i(TAG, "onDestroyView()")
    }
}