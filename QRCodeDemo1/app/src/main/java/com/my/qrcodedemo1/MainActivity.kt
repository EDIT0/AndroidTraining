package com.my.qrcodedemo1

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.my.qrcodedemo1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

//        try {
//            val barcodeEncoder = BarcodeEncoder()
//            val bitmap = barcodeEncoder.encodeBitmap("naversearchapp://default?version=2", BarcodeFormat.QR_CODE, 512, 512)
//            Glide.with(binding.ivQRBarcode.context)
//                .load(bitmap)
//                .override(dpToPx(this@MainActivity, 100f).toInt(), dpToPx(this@MainActivity, 100f).toInt())
//                .into(binding.ivQRBarcode)
//        } catch (e: Exception) {
//            Log.e("MYTAG", "${e}")
//        }

        val qrCodeBitmap = generateQRCode("qrcodedemo1://open/app")
        Glide.with(binding.ivQRBarcode.context)
            .load(qrCodeBitmap)
            .into(binding.ivQRBarcode)

    }

    private fun generateQRCode(text: String): Bitmap {
        val width = 500
        val height = 500
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }

    fun dpToPx(context: Context, dp: Float): Double {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toDouble()
    }

    fun pxToDp(context: Context, px: Float): Double {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toDouble()
    }
}