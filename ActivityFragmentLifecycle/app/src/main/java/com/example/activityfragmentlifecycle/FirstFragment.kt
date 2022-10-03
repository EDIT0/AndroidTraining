package com.example.activityfragmentlifecycle

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.activityfragmentlifecycle.MainActivity.Companion.LifecycleTag
import com.example.activityfragmentlifecycle.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private val TAG = FirstFragment::class.java.simpleName

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(LifecycleTag, "${TAG} onAttach ${context}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(LifecycleTag, "${TAG} onCreate ${savedInstanceState}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(LifecycleTag, "${TAG} onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(LifecycleTag, "${TAG} onViewCreated ${savedInstanceState}")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.i(LifecycleTag, "${TAG} onViewStateRestored ${savedInstanceState}")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.i(LifecycleTag, "${TAG} onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.i(LifecycleTag, "${TAG} onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.i(LifecycleTag, "${TAG} onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.i(LifecycleTag, "${TAG} onStop")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(LifecycleTag, "${TAG} onSaveInstanceState ${outState}")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.i(LifecycleTag, "${TAG} onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        Log.i(LifecycleTag, "${TAG} onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.i(LifecycleTag, "${TAG} onDetach")
        super.onDetach()
    }
}