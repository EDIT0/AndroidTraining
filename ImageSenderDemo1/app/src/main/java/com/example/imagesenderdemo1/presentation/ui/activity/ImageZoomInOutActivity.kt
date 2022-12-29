package com.example.imagesenderdemo1.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.util.DELETE_IMAGE_COMPLETE
import com.example.imagesenderdemo1.data.util.EventBus
import com.example.imagesenderdemo1.databinding.ActivityImageZoomInOutBinding
import com.example.imagesenderdemo1.databinding.ActivityMainBinding
import com.example.imagesenderdemo1.presentation.base.DataBindingBaseActivity
import com.example.imagesenderdemo1.presentation.viewmodel.ImageZoomInOutViewModel
import com.example.imagesenderdemo1.presentation.viewmodel.ImageZoomInOutViewModelFactory
import com.example.imagesenderdemo1.presentation.viewmodel.MainViewModel
import com.example.imagesenderdemo1.presentation.viewmodel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ImageZoomInOutActivity : DataBindingBaseActivity<ActivityImageZoomInOutBinding>(R.layout.activity_image_zoom_in_out) {

    private val TAG = ImageZoomInOutActivity::class.java.simpleName

    lateinit var imageZoomInOutViewModel: ImageZoomInOutViewModel
    @Inject
    lateinit var imageZoomInOutViewModelFactory: ImageZoomInOutViewModelFactory

    private var imageIdx = -1
    private var imageAddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageZoomInOutViewModel = ViewModelProvider(this, imageZoomInOutViewModelFactory).get(ImageZoomInOutViewModel::class.java)

        imageIdx = intent.getIntExtra("image_idx", -1)
        imageAddress = intent.getStringExtra("image_address").toString()

        Glide.with(binding.ivSavedImage.context)
            .load(imageAddress)
            .error(R.drawable.ic_launcher_background)
            .fallback(R.drawable.ic_launcher_background)
            .into(binding.ivSavedImage)

        binding.ivClose.setOnClickListener {
            onBackPressed()
        }

        binding.ivDeleteImage.setOnClickListener {
            if(imageIdx == -1) {
                onBackPressed()
            } else {
                imageZoomInOutViewModel.deleteImage(imageIdx)
            }

        }

        observer()
//        binding.menuLayout.setOnClickListener {
//            binding.menuLayout.visibility = View.INVISIBLE
//        }
//
//        binding.ivSavedImage.setOnClickListener {
//            if(binding.menuLayout.visibility == View.VISIBLE) {
//                binding.menuLayout.visibility = View.INVISIBLE
//            } else {
//                binding.menuLayout.visibility = View.VISIBLE
//            }
//        }
    }

    private fun observer() {
        imageZoomInOutViewModel.isSuccess.observe(mActivity as LifecycleOwner) {
            showToast("이미지 삭제 완료")
            EventBus.post(DELETE_IMAGE_COMPLETE)
            onBackPressed()
        }
    }
}