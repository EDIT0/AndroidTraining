package com.example.viewbindingdemo1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewbindingdemo1.databinding.FragmentOneBinding

class OneFragment : Fragment() {

    private var _fragmentOneBinding: FragmentOneBinding? = null
    private val fragmentOneBinding get() = _fragmentOneBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentOneBinding = FragmentOneBinding.inflate(inflater, container, false)
        return fragmentOneBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentOneBinding.txtFragment.text = "Hello Fragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentOneBinding = null
    }

}