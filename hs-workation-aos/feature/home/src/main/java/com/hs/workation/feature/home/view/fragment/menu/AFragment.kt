package com.hs.workation.feature.home.view.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hs.workation.core.component.CommonRangeCalendar
import com.hs.workation.core.component.CommonSingleCalendarSheet
import com.hs.workation.feature.home.databinding.FragmentABinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentABinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentABinding.inflate(inflater)

        setCalendar(binding)
        setSingleCalendarBottomSheet()

        return binding.root
    }

    /** 범위 선택 달력 */
    private fun setCalendar(binding: FragmentABinding) {
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}