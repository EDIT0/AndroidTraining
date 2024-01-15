package com.example.accordioninfoviewrv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.accordioninfoviewrv.databinding.ActivityMainBinding
import com.example.accordioninfoviewrv.view.AccordionTextInfoModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val arr = ArrayList<AccordionTextInfoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arr.apply {
            add(AccordionTextInfoModel("유의사항", false, "1컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.\n1컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.\n\n1컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다."))
            add(AccordionTextInfoModel("개인정보 제3자 제공동의 개인정보 제3자 제공동의 개인정보 제3자 제공동의 개인정보 제3자 제공동의 개인정보 제3자 제공동의 개인정보 제3자 제공동의", false, "2컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다.컨텐츠입니다."))
        }

        binding.rvAccordion1.apply {
            setTitleTextSize(20)
            setTitleTextColor(R.color.blue)
            setContentsTextSize(18)
            setContentsTextColor(R.color.green)
            setIconSize(0, 20)
            setIcon(R.drawable.ic_arrow_forward_ios_40)
            initializeAndSetList(arr)
        }
        binding.rvAccordion2.apply {
            setTitleTextSize(16)
            setTitleTextColor(R.color.yellow)
            setContentsTextSize(14)
            setContentsTextColor(R.color.red)
            setIconSize(0, null)
            initializeAndSetList(arr)
        }

    }
}