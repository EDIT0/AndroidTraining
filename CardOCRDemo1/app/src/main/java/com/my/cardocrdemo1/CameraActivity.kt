package com.my.cardocrdemo1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.my.cardocrdemo1.constant.Constant
import com.my.cardocrdemo1.databinding.ActivityCameraBinding
import com.my.cardocrdemo1.util.ScreenUtil
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private val cameraViewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 카드 인식 프리뷰 크기 셋팅
        val (width, height) = cardPreviewResizing()
        val layoutParams = binding.preview.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        binding.preview.layoutParams = layoutParams

        // 가로 꽉차게 FILL_CENTER -> 이에 따라 Height가 임의로 정해짐
        // 틀 비율에 맞춰 FIT_CENTER
        binding.preview.scaleType = PreviewView.ScaleType.FILL_CENTER

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            cameraViewModel.initCamera(
                view = binding.preview,
                lifecycleOwner = this@CameraActivity
            )
                .catch {
                    binding.progressBar.visibility = View.GONE
                    finish()
                }
                .collect {
                    binding.progressBar.visibility = View.GONE
                    cameraViewModel.takePicture(it)
                    Log.d("MYTAG", "Preview Size: ${binding.preview.width} / ${binding.preview.height}")
                }
        }

        lifecycleScope.launch {
            cameraViewModel.isTakePicture.collect {
                if(!it) {
                    val model = CardInfoModel(number = cameraViewModel.cardNumber, date = cameraViewModel.cardDate)
                    val intent = Intent()
                    intent.putExtra(Constant.CARD_INFO, model)
                    setResult(Constant.ACTIVITY_RETURN_CODE_CARD_ACTIVITY, intent)
                    finish()
                }
            }
        }
    }

    private fun cardPreviewResizing(): Pair<Int, Int> {
        val (width, height) = ScreenUtil.getScreenWidthHeight(this)

        Log.d("MYTAG", "휴대폰 스크린 크기: ${width} / ${height}")

        val calWidth: Int = (width / 1.8).toInt()
        val calHeight: Int = (height / 1).toInt()

        if(calWidth >= calHeight) {
            val previewWidth = ((calHeight * 1.8).toInt() / PREVIEW_MARGIN).toInt()
            val previewHeight = ((calHeight * 1).toInt() / PREVIEW_MARGIN).toInt()
            return Pair<Int, Int>(previewWidth, previewHeight)
        } else {
            val previewWidth = ((calWidth * 1.8).toInt() / PREVIEW_MARGIN).toInt()
            val previewHeight = ((calWidth * 1).toInt() / PREVIEW_MARGIN).toInt()
            return Pair<Int, Int>(previewWidth, previewHeight)
        }
    }

    override fun onPause() {
        super.onPause()

        finish()
    }

    companion object {
        const val PREVIEW_MARGIN = 1.2
    }
}