package com.my.viewtransitionanimdemo1.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.fragment.app.setFragmentResult
import com.my.viewtransitionanimdemo1.R
import com.my.viewtransitionanimdemo1.activity.FragmentActivity
import com.my.viewtransitionanimdemo1.databinding.FragmentFirstBinding
import com.my.viewtransitionanimdemo1.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val activity by lazy {
        requireActivity() as FragmentActivity
    }

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val resultBundle = Bundle().apply {
                    putString("resultStringKey", "Result from SecondFragment")
                    putInt("resultIntKey", 123)
                }
                setFragmentResult("requestKey", resultBundle)
                (requireActivity() as FragmentActivity).fragmentNavController?.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataString = arguments?.getString("dataString")
        val dataInt = arguments?.getInt("dataInt")

        Log.d("MYTAG", "From FirstFragment, ${dataString} / ${dataInt}")

        // 트랜지션 애니메이션 설정
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}