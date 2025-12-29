package com.my.xmlnavigationdemo1.bottomnav.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.xmlnavigationdemo1.R
import com.my.xmlnavigationdemo1.bottomnav.fragment.adapter.StringAdapter
import com.my.xmlnavigationdemo1.bottomnav.fragment.viewmodel.AOneViewModel
import com.my.xmlnavigationdemo1.databinding.FragmentAOneBinding

class AOneFragment : Fragment() {

    private var _binding: FragmentAOneBinding? = null
    val binding get() = _binding!!

    private val aOneVM: AOneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MYTAG ${javaClass.simpleName}", "onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("MYTAG ${javaClass.simpleName}", "onViewCreated()")

        _binding = FragmentAOneBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reFetch()

        binding.rvOne.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOne.adapter = StringAdapter(
            arrayOf(
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "a",
                "b",
                "c"
            )
        )

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_AOneFragment_to_ATwoFragment)
        }
    }

    private fun reFetch() {
        binding.svOne.post {
            binding.svOne.scrollTo(0, aOneVM.scrollPosition)
        }

        binding.etOne.setText(aOneVM.etText)
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
        aOneVM.apply {
            scrollPosition = binding.svOne.scrollY
            etText = binding.etOne.text.toString()
        }

        super.onDestroyView()
        _binding = null

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("MYTAG ${javaClass.simpleName}", "onDestroy()")
    }
}