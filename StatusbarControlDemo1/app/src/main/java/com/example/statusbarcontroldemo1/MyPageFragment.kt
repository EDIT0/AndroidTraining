package com.example.statusbarcontroldemo1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.statusbarcontroldemo1.Util.statusBarHeight
import com.example.statusbarcontroldemo1.base.BaseFragment
import com.example.statusbarcontroldemo1.databinding.FragmentHomeBinding
import com.example.statusbarcontroldemo1.databinding.FragmentMyPageBinding


class MyPageFragment : BaseFragment() {

    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")

        binding.scrollView.apply {
            clipToPadding = false
            setPadding(0, binding.root.context.statusBarHeight(), 0, 0) // 컨텐츠를 보여주는 뷰가 StatusBar의 아이콘들에 의해 가려지지 않도록 설정
        }

        binding.textView.apply {
            text = getString(R.string.long_text)
        }
    }

    override fun onDestroyView() {
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")
        super.onDestroyView()
        _binding = null
    }

}