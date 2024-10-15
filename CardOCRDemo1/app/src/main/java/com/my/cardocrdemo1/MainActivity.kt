package com.my.cardocrdemo1

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.my.cardocrdemo1.constant.Constant
import com.my.cardocrdemo1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val launchActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("MYTAG", "launchActivity ${result.resultCode} / ${result.data}")
        when (result.resultCode) {
            Constant.ACTIVITY_RETURN_CODE_CARD_ACTIVITY -> {
                Log.d("MYTAG", "여기서 데이터 받기")
                Log.d("MYTAG", "받은 데이터: ${result.data?.getParcelableExtra<Parcelable>(Constant.CARD_INFO)}")
                val cardInfoModel = result.data?.getParcelableExtra(Constant.CARD_INFO)?: CardInfoModel("", "")

                val filterMonth = cardInfoModel.date.substring(0, 2)
                val filterYear = cardInfoModel.date.substring(cardInfoModel.date.length-2, cardInfoModel.date.length)

                val filterNumber = cardInfoModel.number.replace("-", "").replace("/", "")
                val filteredList = filterNumber.split(" ")
                val sb = StringBuilder()
                for(i in 0 until filteredList.size) {
                    if(filteredList[i].isNotEmpty()) {
                        if(i == filteredList.size - 1) {
                            sb.append("${filteredList[i]}")
                        } else {
                            sb.append("${filteredList[i]} ")
                        }
                    }
                }

                Log.d("MYTAG","카드 번호: ${sb}")

                mainViewModel.setCardInfo(CardInfoModel(number = sb.toString(), date = "${filterMonth} ${filterYear}"))

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnTakePicture.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launchActivity.launch(intent)
        }

        lifecycleScope.launch {
            mainViewModel.cardInfoModel
                .collect { data ->
                    Log.d("MYTAG", "CardInfoModel Collect : ${data}")
                    val numberList = data.number.split(" ")
                    val dateList = data.date.split(" ")

                    for(i in numberList) {
                        Log.d("MYTAG", "${i}")
                    }

                    if(numberList.size >= 4 && dateList.size >= 2) {
                        binding.apply {
                            etNumber1.setText(numberList[0])
                            etNumber2.setText(numberList[1])
                            etNumber3.setText(numberList[2])
                            etNumber4.setText(numberList[3])

                            etMonth.setText(dateList[0])
                            etYear.setText(dateList[1])
                        }
                    }
                }
        }
    }
}