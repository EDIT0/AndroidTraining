package com.example.multipleimagepicker.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.data.util.Utility
import com.example.domain.model.ImagePickerModel
import com.example.multipleimagepicker.R
import com.example.multipleimagepicker.adapter.ImageDecoViewPagerAdapter
import com.example.multipleimagepicker.databinding.ActivityImageDecoViewPagerBinding
import com.example.multipleimagepicker.ui.base.DataBindingBaseActivity
import com.example.multipleimagepicker.viewmodel.ImageDecoViewPagerViewModel
import com.example.multipleimagepicker.viewmodel.ImageDecoViewPagerViewModelFactory
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
            imageDecoViewPagerViewModel.setOriginalImageList(intent.getParcelableArrayListExtra<ImagePickerModel>("uris")?:return onBackPressed())
            Log.i("MYTAG", "넘어온 이미지 갯수: ${intent.getParcelableArrayListExtra<ImagePickerModel>("uris")?.size}")
        } catch (e : Exception) {
            Log.i("MYTAG", "${e.message}")
        }
        val decoList = ArrayList<Uri>()
        for(i in imageDecoViewPagerViewModel.originalImageList.value!!) {
            Log.i("MYTAG", "${i}")
            decoList.add(i.uri)
        }
        imageDecoViewPagerViewModel.setDecoImageList(decoList)
        imageDecoViewPagerViewModel.decoImageCount.value = imageDecoViewPagerViewModel.decoImageList.value?.size?:return onBackPressed()
    }

    private fun viewPagerSetting() {
        binding.vpImage.adapter = imageDecoViewPagerAdapter

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
            imageDecoViewPagerViewModel.saveDecoImageListUriToOriginalImageList()

            val intent = Intent(binding.root.context, MultipleImagePickerActivity::class.java).apply {
                putParcelableArrayListExtra("uris", imageDecoViewPagerViewModel.originalImageList.value as ArrayList)
            }
            setResult(RESULT_OK, intent)
            onBackPressed()
        }

        binding.ibCrop.setOnClickListener {
            val uri = imageDecoViewPagerViewModel.decoImageList.value!!.get(imageDecoViewPagerViewModel.decoImageCurrentCount.value!! - 1)

            CropImage.activity(uri)
                .setAutoZoomEnabled(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(mActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                Log.i("MYTAG", "크롭 후 만들어진 uri: ${result.uri}")
                val resultUri = result.uri
                imageDecoViewPagerViewModel.changeImage((imageDecoViewPagerViewModel.decoImageCurrentCount.value!! - 1), resultUri!!)

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}