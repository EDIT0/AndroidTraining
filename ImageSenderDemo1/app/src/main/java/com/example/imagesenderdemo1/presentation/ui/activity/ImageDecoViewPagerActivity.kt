package com.example.imagesenderdemo1.presentation.ui.activity

import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.util.Utility
import com.example.imagesenderdemo1.databinding.ActivityImageDecoViewPagerBinding
import com.example.imagesenderdemo1.presentation.adapter.ImageDecoViewPagerAdapter
import com.example.imagesenderdemo1.presentation.base.DataBindingBaseActivity
import com.example.imagesenderdemo1.presentation.viewmodel.ImageDecoViewPagerViewModel
import com.example.imagesenderdemo1.presentation.viewmodel.ImageDecoViewPagerViewModelFactory
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class ImageDecoViewPagerActivity : DataBindingBaseActivity<ActivityImageDecoViewPagerBinding>(R.layout.activity_image_deco_view_pager) {

    lateinit var imageDecoViewPagerViewModel: ImageDecoViewPagerViewModel
    @Inject
    lateinit var imageDecoViewPagerViewModelFactory: ImageDecoViewPagerViewModelFactory

    private val imageDecoViewPagerAdapter = ImageDecoViewPagerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageDecoViewPagerViewModel = ViewModelProvider(this, imageDecoViewPagerViewModelFactory).get(ImageDecoViewPagerViewModel::class.java)
        binding.imageDecoViewPagerVM = imageDecoViewPagerViewModel

        intentSetting()
        viewPagerSetting()
        observe()
        buttonClickListener()
    }

    private fun intentSetting() {
        try {
            imageDecoViewPagerViewModel.setDecoImageList(intent.getParcelableArrayListExtra<Uri>("uris")?:return onBackPressed())
            Log.i("MYTAG", "넘어온 이미지 갯수: ${intent.getParcelableArrayListExtra<Uri>("uris")?.size}")
        } catch (e : Exception) {
            Log.i("MYTAG", "${e.message}")
        }
        imageDecoViewPagerViewModel.decoImageCount.value = intent.getParcelableArrayListExtra<Uri>("uris")?.size
    }

    private fun viewPagerSetting() {
        binding.vpImage.adapter = imageDecoViewPagerAdapter

        for(i in imageDecoViewPagerViewModel.decoImageList.value!!) {
            Log.i("MYTAG", "${i}")
        }

        binding.vpImage.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                imageDecoViewPagerViewModel.decoImageCurrentCount.value = (position + 1)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun observe() {
        imageDecoViewPagerViewModel.decoImageList.observe(mActivity as LifecycleOwner) {
            imageDecoViewPagerAdapter.submitList(it)
            imageDecoViewPagerAdapter.notifyDataSetChanged()
        }
    }

    private fun buttonClickListener() {
        binding.ibClose.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpload.setOnClickListener {
            val intent = Intent(binding.root.context, MainActivity::class.java).apply {
                putParcelableArrayListExtra("uris", imageDecoViewPagerViewModel.decoImageList.value as ArrayList)
            }
            setResult(RESULT_OK, intent)
            onBackPressed()
        }

        binding.ibCrop.setOnClickListener {
            Utility.cropImage(mActivity, imageDecoViewPagerViewModel.decoImageList.value!!.get(imageDecoViewPagerViewModel.decoImageCurrentCount.value!! - 1))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                imageDecoViewPagerViewModel.changeImage((imageDecoViewPagerViewModel.decoImageCurrentCount.value!! - 1), resultUri!!)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}