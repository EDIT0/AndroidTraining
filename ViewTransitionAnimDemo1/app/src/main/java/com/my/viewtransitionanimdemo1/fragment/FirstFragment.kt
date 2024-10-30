package com.my.viewtransitionanimdemo1.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.my.viewtransitionanimdemo1.R
import com.my.viewtransitionanimdemo1.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnOpenSecondFragment.setOnClickListener {
            val dataString = "Hello from FirstFragment"
            val dataInt = 42

            val bundle = Bundle().apply {
                putString("dataString", dataString)
                putInt("dataInt", dataInt)
            }

            val extras = FragmentNavigatorExtras(
                binding.btnOpenSecondFragment to "main"
            )

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle, null, extras)
        }

        binding.ivAndroid.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.ivAndroid to "ivAndroid"
            )

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, null, null, extras)
        }


        setFragmentResultListener("requestKey") { _, bundle ->
            val resultStringData = bundle.getString("resultStringKey") // "Result from SecondFragment"
            val resultIntData = bundle.getInt("resultIntKey") // "Result from SecondFragment"
            Log.d("MYTAG", "resultData : ${resultStringData} / ${resultIntData}")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}