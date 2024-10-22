package com.example.bottomnavigation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bottomnavigation.viewmodel.OneViewModel
import com.example.bottomnavigation.view.activity.MainActivity
import com.example.bottomnavigation.databinding.FragmentOneBinding
import com.example.bottomnavigation.event.CenterTextUiEvent
import com.example.bottomnavigation.event.OneViewModelEvent
import kotlinx.coroutines.launch

class OneFragment : Fragment() {

    private val TAG = "MYTAG"

    private var _fragmentOneBinding: FragmentOneBinding? = null
    private val fragmentOneBinding get() = _fragmentOneBinding!!

    private val oneViewModel: OneViewModel by viewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            oneViewModel.centerTextUiState
                .collect {
                    fragmentOneBinding.tvOne.text = it.centerText
                }
        }

        fragmentOneBinding.tvOne.setOnClickListener {
            (requireActivity() as MainActivity).showToast("${TAG}")
            oneViewModel.handleViewModelEvent(oneViewModelEvent = OneViewModelEvent.ChangeCenterText("\nOne"))
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