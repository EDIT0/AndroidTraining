package com.hs.workation.feature.splash.test1.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.model.base.SideEffectEvent
import com.hs.workation.core.util.LogUtil
import com.hs.workation.core.model.test.req.ReqTest2
import com.hs.workation.core.model.test.res.ResTest1
import com.hs.workation.core.model.test.res.ResTest2
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentTest1Binding
import com.hs.workation.feature.splash.test1.event.Test1NoStateUiEvent
import com.hs.workation.feature.splash.test1.event.Test1UiErrorEvent
import com.hs.workation.feature.splash.test1.event.Test1ViewModelEvent
import com.hs.workation.feature.splash.main.view.activity.SplashActivity
import com.hs.workation.feature.splash.test1.view.adapter.LoadStateAdapter
import com.hs.workation.feature.splash.test1.view.viewmodel.Test1ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Test1Fragment : BaseDataBindingFragment<FragmentTest1Binding>(R.layout.fragment_test_1) {

    private val test1VM: Test1ViewModel by viewModels()
    private val activity by lazy {
        requireActivity() as SplashActivity
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
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)

        val dataString = arguments?.getString("dataString")
        val dataInt = arguments?.getInt("dataInt")

        LogUtil.d_dev("Test1Fragment, ${dataString} / ${dataInt}")

        requestViewModelEvent(Test1ViewModelEvent.GetTest1())
        if(test1VM.test1Adapter.itemCount == 0) {
            requestViewModelEvent(Test1ViewModelEvent.GetTest2(reqTest2 = ReqTest2(1)))
        }

        collector(test1VM)

        initRvAndListener()

        viewClickListener()
    }

    private fun collector(test1VM: Test1ViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            test1VM.test1UiState.collect {
                it.resTest1?.let { resTest1 ->
                    updateResTest1(resTest1)
                }

                it.resTest2?.let { resTest2 ->
                    updateResTest2(resTest2)
                }

                it.questions?.let { questions ->
                    updateQuestionList(questions)
                }

                // 리프래시 끝나면 없애기
                binding.layoutSwipeRefresh.isRefreshing = false
            }
        }

        /**
         * 에러 처리 (각 에러에 맞게 추가 가능)
         */
        viewLifecycleOwner.lifecycleScope.launch {
            test1VM.test1UiErrorEvent.collect {
                when(it) {
                    is Test1UiErrorEvent.ConnectionError -> {

                    }
                    is Test1UiErrorEvent.DataEmpty -> {

                    }
                    is Test1UiErrorEvent.ExceptionHandle -> {

                    }
                    is Test1UiErrorEvent.Fail -> {

                    }
                    is Test1UiErrorEvent.Init -> { }
                }
            }
        }

        /**
         * 단발성 이벤트
         */
        viewLifecycleOwner.lifecycleScope.launch {
            test1VM.test1NoStateUiEvent.collect {
                when(it) {
                    is Test1NoStateUiEvent.UpdateIsDeleteListItem -> {
                        if(it.isDelete) {
                            // 삭제 성공
                            test1VM.test1Adapter.refresh()
                        } else {
                            // 삭제 실패
                        }
                    }
                }
            }
        }

        /**
         * 공통 이벤트
         */
        viewLifecycleOwner.lifecycleScope.launch {
            test1VM.sideEffectEvent.collect {
                when(it) {
                    is SideEffectEvent.NetworkError -> {
                        showToast(it.message)
                    }
                }
            }
        }
    }

    private fun initRvAndListener() {
        binding.rvStudentHistory.apply {
            adapter = test1VM.test1Adapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }
        LogUtil.d("Test1Adapter Count : ${test1VM.test1Adapter.itemCount}")

        // 리스트 새로고침
        binding.layoutSwipeRefresh.setOnRefreshListener {
            test1VM.test1Adapter.refresh()
        }

        test1VM.test1Adapter.addLoadStateListener {
            LogUtil.i_dev("prepend Loading ${it.source.prepend is LoadState.Loading}")
            LogUtil.i_dev("append Loading ${it.source.append is LoadState.Loading}")
            LogUtil.i_dev("refresh Loading ${it.source.refresh is LoadState.Loading}")

            if(it.source.refresh is LoadState.Loading) {
                LogUtil.d_dev("첫 로딩 중 일 때")
            } else {
                LogUtil.d_dev("로딩 중이 아닐 때")

                val errorState = when {
                    it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                    it.append is LoadState.Error -> it.append as LoadState.Error
                    it.refresh is LoadState.Error -> it.refresh as LoadState.Error
                    else -> null
                }

                LogUtil.d_dev("페이징 에러: ${errorState}")
                val errorMessage = errorState?.error?.message
            }
        }

        // retry 말그대로 실패 후 재시도
        binding.apply {
            rvStudentHistory.setHasFixedSize(true) // 사이즈 고정
            // header, footer 설정
            rvStudentHistory.adapter = test1VM.test1Adapter.withLoadStateHeaderAndFooter(
                header = LoadStateAdapter { test1VM.test1Adapter.retry() },
                footer = LoadStateAdapter { test1VM.test1Adapter.retry() }
            )
        }

        test1VM.test1Adapter.setOnItemClickListener { i, testQuestion ->
            requestViewModelEvent(Test1ViewModelEvent.DeleteTest2Question(testQuestion))
            showToast("${i}번째 데이터 ${testQuestion.title} 삭제")
        }
    }

    private fun updateResTest1(resTest1: ResTest1) {
        LogUtil.d_dev("학생 정보 업데이트 ${resTest1}")
        binding.tvStudentInfo.text = if(resTest1.name.isNotEmpty()) {
            resTest1.name + " / " + resTest1.number + " / " + resTest1.avgScore
        } else {
            "정보 없음"
        }
    }

    private fun updateResTest2(resTest2: ResTest2) {
        LogUtil.d_dev("과목 업데이트 ${resTest2}")
        binding.tvSubjectInfo.text = if(!resTest2?.subjectName.isNullOrEmpty() && resTest2?.subjectNumber != null) {
            resTest2.subjectName + " / " + resTest2.subjectNumber
        } else {
            "정보 없음"
        }
    }

    private fun updateQuestionList(questions: PagingData<ResTest2.TestQuestion>) {
        LogUtil.d_dev("리스트 업데이트 ${questions}")
        viewLifecycleOwner.lifecycleScope.launch {
            delay(100L)
            test1VM.test1Adapter.submitData(this@Test1Fragment.lifecycle, questions)
        }
    }

    private fun viewClickListener() {
        binding.btnToTest2Fragment.setOnClickListener {
            findNavController().navigate(R.id.action_test1Fragment_to_test2Fragment, args = null, navOptions = null, navigatorExtras = null)
        }
    }

    private fun requestViewModelEvent(test1ViewModelEvent: Test1ViewModelEvent) {
        when(test1ViewModelEvent) {
            is Test1ViewModelEvent.GetTest1 -> {
                test1VM.handleViewModelEvent(test1ViewModelEvent)
            }
            is Test1ViewModelEvent.GetTest2 -> {
                test1VM.handleViewModelEvent(test1ViewModelEvent)
            }
            is Test1ViewModelEvent.DeleteTest2Question -> {
                test1VM.handleViewModelEvent(test1ViewModelEvent)
            }
        }
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}