package com.example.calendarexample1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.calendarexample1.Decorators.*
import com.example.calendarexample1.EventCalendar.EventCalendarActivity
import com.example.calendarexample1.RangeCalendar.RangeCalendarActivity
import com.example.calendarexample1.databinding.ActivityMainBinding
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        val TAG_C = javaClass.name
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRangeCalendar.setOnClickListener {
            startActivity(Intent(this, RangeCalendarActivity::class.java))
        }

        binding.btnEventCalendar.setOnClickListener {
            startActivity(Intent(this, EventCalendarActivity::class.java))
        }
    }
}