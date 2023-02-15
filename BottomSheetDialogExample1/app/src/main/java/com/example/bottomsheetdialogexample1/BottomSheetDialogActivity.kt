package com.example.bottomsheetdialogexample1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bottomsheetdialogexample1.databinding.ActivityBottomSheetDialogBinding

class BottomSheetDialogActivity : AppCompatActivity() {

    private val TAG = BottomSheetDialogActivity::class.java.simpleName

    private lateinit var binding : ActivityBottomSheetDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomSheetDialogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnShowBottomSheet.setOnClickListener {
            val bottomSheetDialog1 = BottomSheetDialog1Fragment()


            val bundle = Bundle()
            bundle.putString("dataString", "abc")
            bottomSheetDialog1.arguments = bundle
            bottomSheetDialog1.show((binding.root.context as AppCompatActivity).supportFragmentManager, "BottomSheetDialog")
        }

    }
}