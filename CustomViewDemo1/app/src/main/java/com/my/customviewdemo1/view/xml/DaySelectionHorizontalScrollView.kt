package com.my.customviewdemo1.view.xml

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.customviewdemo1.LogUtil
import com.my.customviewdemo1.databinding.DaySelectionHorizontalScrollViewBinding
import com.my.customviewdemo1.dto.DaySelectionModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId

class DaySelectionHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private lateinit var binding: DaySelectionHorizontalScrollViewBinding

    private lateinit var daySelectionHorizontalScrollAdapter: DaySelectionHorizontalScrollAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var currentLastDay = 0

    init {
        binding = DaySelectionHorizontalScrollViewBinding.inflate(LayoutInflater.from(context), this, true)

        daySelectionHorizontalScrollAdapter = DaySelectionHorizontalScrollAdapter()

        linearLayoutManager = LinearLayoutManager(binding.rvDays.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDays.layoutManager = linearLayoutManager
        binding.rvDays.adapter = daySelectionHorizontalScrollAdapter
    }

    fun setDayClickCallback(clickListener: DaySelectionHorizontalScrollAdapter.DayClickListener) {
        daySelectionHorizontalScrollAdapter.setDayClickCallback(object : DaySelectionHorizontalScrollAdapter.DayClickListener {
            override fun onDayClicked(year: Int, month: Int, day: Int) {
                val dayList = ArrayList<DaySelectionModel>()

                for(i in 1 .. currentLastDay) {
                    var isClicked = false
                    if(day == i) {
                        isClicked = true
                    }
                    dayList.add(DaySelectionModel(i, year, month, i, isClicked))
                }
                daySelectionHorizontalScrollAdapter.submitList(dayList.toMutableList()) {
                    daySelectionHorizontalScrollAdapter.notifyDataSetChanged()
                    binding.rvDays.smoothScrollToPosition(day-1)
                }

                clickListener.onDayClicked(year, month, day)
            }
        })
    }

    /**
     * 원하는 년, 월, 일로 설정
     *
     * @param year
     * @param month
     * @param day
     */
    fun setDate(year: Int, month: Int, day: Int) {
        val lastDay = YearMonth.of(year, month).lengthOfMonth()
        currentLastDay = lastDay
        LogUtil.d_dev("마지막 날: ${lastDay}")

        val dayList = ArrayList<DaySelectionModel>()
        for (i in 1..lastDay) {
            var isClicked = false
            if(day == i) {
                isClicked = true
            }
            dayList.add(DaySelectionModel(idx = i, year, month, i, isClicked))
        }
        daySelectionHorizontalScrollAdapter.submitList(dayList.toMutableList()) {
            daySelectionHorizontalScrollAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 오늘 날짜로 설정
     *
     */
    fun todaySetting() {
        val today = LocalDate.now(ZoneId.of("Asia/Seoul"))
        val lastDay = YearMonth.of(today.year, today.monthValue).lengthOfMonth() // 현재 달의 마지막 날
        val todayDay = today.dayOfMonth // 오늘 일

        currentLastDay = lastDay

        val dayList = ArrayList<DaySelectionModel>()
        for (i in 1..lastDay) {
            var isClicked = false
            if (todayDay == i) {
                isClicked = true
            }
            dayList.add(DaySelectionModel(i, today.year, today.monthValue, i, isClicked))
        }
        daySelectionHorizontalScrollAdapter.submitList(dayList.toMutableList()) {
            daySelectionHorizontalScrollAdapter.notifyDataSetChanged()
            binding.rvDays.scrollToPosition(todayDay-1)
        }
    }

    /**
     * 색 설정
     *
     * @param selectedBgDrawable
     * @param unselectedBgDrawable
     * @param selectedTextColor
     * @param unselectedTextColor
     */
    fun setDayBgAndTextColor(selectedBgDrawable: Int, unselectedBgDrawable: Int, selectedTextColor: Int, unselectedTextColor: Int) {
        daySelectionHorizontalScrollAdapter.setDayBgAndTextColor(selectedBgDrawable, unselectedBgDrawable, selectedTextColor, unselectedTextColor)
    }
}