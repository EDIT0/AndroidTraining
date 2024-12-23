package com.hs.workation.feature.splash.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.CommonNumberPickerSheet
import com.hs.workation.core.component.CommonRangeCalendar
import com.hs.workation.core.component.CommonRangeCalendarSheet
import com.hs.workation.core.component.CommonSingleCalendarSheet
import com.hs.workation.core.component.ComposeDatePick
import com.hs.workation.core.util.OnSingleClickListener.onSingleClick
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonCalendarBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class CommonCalendarFragment : BaseDataBindingFragment<FragmentCommonCalendarBinding>(R.layout.fragment_common_calendar) {

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendar()
        setComposeCalendar()
        setSingleCalendarBottomSheet()
    }

    /** 범위 선택 달력 */
    private fun setCalendar() {
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

    /** 내가 선택한 날짜 달력 */
    private fun setComposeCalendar() {
        binding.cvCalendar.setContent {
            val selectedDates = remember { mutableStateOf<List<CalendarDay>?>(null)}
            val selectedDateString = remember { mutableStateOf<String?>(null) }
            val selectedPeople = remember { mutableStateOf<Int?>(null) }

            LaunchedEffect(Unit) {
                /** TODO : delete mockup value
                 * 초기값 테스트 하려면 아래 코드를 주석 해제
                 */
//                val formatter = DateTimeFormatter.ofPattern("M.d (EEE)", Locale.KOREAN)
//                val today = CalendarDay.today()
//                selectedDates.value = listOf(today, CalendarDay.from(today.year, today.month, today.day + 1))
//                selectedPeople.value = 1
//                selectedDates.value?.let { dates ->
//                    val days = "${dates.first().date.format(formatter)} - ${dates.last().date.format(formatter)}"
//                    val nights = "${dates.size - 1}박"
//                    selectedDateString.value = "$days • $nights"
//                }
            }

            ComposeDatePick(
                dateString = selectedDateString.value,
                people = selectedPeople.value,
                onClickDate = {
                    CommonRangeCalendarSheet(
                        initialDates = selectedDates,
                        resultCallback = { dates, dateString ->
                            selectedDates.value = dates
                            selectedDateString.value = dateString
                        }
                    ).show(requireActivity().supportFragmentManager, "")
                },
                onClickPeople = {
                    CommonNumberPickerSheet(
                        title = "이용 인원",
                        peopleCount = selectedPeople.value,
                        resultCallback = {
                            selectedPeople.value = it
                        }
                    ).show(requireActivity().supportFragmentManager, "")
                }
            )
        }
    }

    /** 바텀 시트 달력 */
    private fun setSingleCalendarBottomSheet() {
        binding.btnCalendarSheet.onSingleClick {
            CommonSingleCalendarSheet("테스트 달력") {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.show(requireActivity().supportFragmentManager, "")
        }
    }
}