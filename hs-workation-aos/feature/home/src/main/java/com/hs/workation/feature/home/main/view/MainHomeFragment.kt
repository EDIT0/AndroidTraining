package com.hs.workation.feature.home.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonRangeCalendar
import com.hs.workation.core.component.CommonSingleCalendarSheet
import com.hs.workation.core.util.LogUtil
import com.hs.workation.feature.home.R
import com.hs.workation.feature.home.databinding.FragmentMainHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainHomeFragment : BaseDataBindingFragment<FragmentMainHomeBinding>(R.layout.fragment_main_home) {

    private val mainHomeVM: MainHomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendar(binding)
        setSingleCalendarBottomSheet()
    }

    /** 범위 선택 달력 */
    private fun setCalendar(binding: FragmentMainHomeBinding) {
        val commonRangeCalendar = CommonRangeCalendar()

        with (binding.calendarView) {
            // 달력 생성
            commonRangeCalendar.create(calendarRange)

            // 시작 날짜 콜렉트
            viewLifecycleOwner.lifecycleScope.launch {
                commonRangeCalendar.startDate.collectLatest {
                    binding.startDate.text = it
                }
            }

            // 종료 날짜 콜렉트
            viewLifecycleOwner.lifecycleScope.launch {
                commonRangeCalendar.endDate.collectLatest {
                    binding.endDate.text = it
                }
            }

            // 총 날짜 범위 콜렉트
            viewLifecycleOwner.lifecycleScope.launch {
                commonRangeCalendar.totalDateCount.collectLatest {
                    binding.totalCount.text = it
                }
            }
        }
    }

    /** 바텀 시트 달력 */
    private fun setSingleCalendarBottomSheet() {
        binding.btnCalendarSheet.setOnClickListener {
            CommonSingleCalendarSheet("테스트 달력") {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.show(requireActivity().supportFragmentManager, "")
        }
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onResume()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.d_dev("MYTAG ${javaClass.simpleName} onDestroyView()")
    }
}