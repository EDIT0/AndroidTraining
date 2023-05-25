package com.edit.customsnackbarexample1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.edit.customsnackbarexample1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        App().showCustomTopSnackBar(binding.root, "메인 액티비티에 오신 것을 환영합니다.", 3000L)

        val str = "깜짝 할인 진행 중!!!\n최대 60% 할인된 가격으로 지금 바로 만나보세요."
        val customTopSnackBar = CustomTopSnackBar(binding.root, str, 2000L)
        val customBottomSnackBar = CustomBottomSnackBar(binding.root, str, 0L)

        binding.btnTopSnackBar.setOnClickListener {
            customTopSnackBar.show()
        }

        binding.btnBottomSnackBar.setOnClickListener {
            customBottomSnackBar.show()
        }

        binding.btnSecondActivity.setOnClickListener {
            secondActivityLauncher.launch(Intent(binding.root.context, SecondActivity::class.java))
        }
    }

    val secondActivityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            // RESULT_OK일 때 실행할 코드...

            App().showCustomTopSnackBar(binding.root, "메인 액티비티에 다시 오신 것을 환영합니다.", 3000L)
        }
    }
}