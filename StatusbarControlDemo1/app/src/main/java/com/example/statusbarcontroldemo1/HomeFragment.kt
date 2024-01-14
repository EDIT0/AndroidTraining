package com.example.statusbarcontroldemo1

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.statusbarcontroldemo1.Util.statusBarHeight
import com.example.statusbarcontroldemo1.base.BaseFragment
import com.example.statusbarcontroldemo1.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")
        homeViewModel = HomeViewModel(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")

//        homeViewModel.setTxtState(HomeViewModel.LONG)

        binding.scrollView.apply {
            clipToPadding = false
            setPadding(0, binding.root.context.statusBarHeight(), 0, 0) // 컨텐츠를 보여주는 뷰가 StatusBar의 아이콘들에 의해 가려지지 않도록 설정
        }

        binding.textView.apply {
            setOnClickListener {
                homeViewModel.setTxtState(HomeViewModel.LONG_2)
                val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.apply {
                    replace(R.id.fragment_container, MyPageFragment(), MyPageFragment().tag)
                    addToBackStack(null)
                    commit()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.txtState
                .collect {
                    Log.d("MYTAG", "txtState update: ${it}")
                    binding.textView.text = it
                }
        }
    }

    override fun onDestroyView() {
        Log.d("MYTAG", "${javaClass.simpleName} ${Thread.currentThread().stackTrace[2].methodName}")
        super.onDestroyView()
        _binding = null
    }

}