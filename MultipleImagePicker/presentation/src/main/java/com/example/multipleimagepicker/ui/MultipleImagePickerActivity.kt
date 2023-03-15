package com.example.multipleimagepicker.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.data.util.MessageSet
import com.example.domain.model.ImagePickerModel
import com.example.multipleimagepicker.adapter.ImagePickerAdapter
import com.example.multipleimagepicker.adapter.SelectedImageAdapter
import com.example.multipleimagepicker.databinding.ActivityMultipleImagePickerBinding
import com.example.multipleimagepicker.ui.base.ViewBindingBaseActivity
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModel
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MultipleImagePickerActivity : ViewBindingBaseActivity<ActivityMultipleImagePickerBinding>() {

//    private lateinit var binding: ActivityMultipleImagePickerBinding

    @Inject
    lateinit var imagePickerViewModelFactory: ImagePickerViewModelFactory
    lateinit var imagePickerViewModel: ImagePickerViewModel
//    private val imagePickerViewModel: ImagePickerViewModel by viewModels()

    private val selectedImageAdapter = SelectedImageAdapter()
    private val imagePickerAdapter = ImagePickerAdapter()

    override fun getBinding(): ActivityMultipleImagePickerBinding = ActivityMultipleImagePickerBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMultipleImagePickerBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        imagePickerViewModel = ViewModelProvider(this, imagePickerViewModelFactory)[ImagePickerViewModel::class.java]

        buttonClickListener()
        currentSelectedImageRVSetting()
        imagePickerRVSetting()
        observe()

        imagePickerViewModel.setImageLimitationCount(7)
        imagePickerViewModel.getAlbumImageList()
    }

    private fun buttonClickListener() {
        binding.ibClose.setOnClickListener {
            onBackPressed()
        }

        binding.btnDone.setOnClickListener {
            val intent = Intent(binding.root.context, MainActivity::class.java).apply {
                putParcelableArrayListExtra("uris", imagePickerViewModel.selectedImageItemList.value as ArrayList)
            }
            setResult(RESULT_OK, intent)
            onBackPressed()
        }
    }

    private fun currentSelectedImageRVSetting() {
        binding.rvCurrentSelectionImage.adapter = selectedImageAdapter

        selectedImageAdapter.setOnItemClickListener { i, imagePickerModel ->
            imagePickerViewModel.setCheckedForSelectedImage(i)
        }
    }

    private fun imagePickerRVSetting() {
        binding.rvImage.adapter = imagePickerAdapter

        imagePickerAdapter.setOnItemClickListener { i, imagePickerModel ->
            imagePickerViewModel.setCheckedForImagePicker(i, !(imagePickerModel.isChecked))
        }
    }

    private fun observe() {
        imagePickerViewModel.eventObserver.observe(binding.root.context as LifecycleOwner) {
            when(it) {
                MessageSet.IMAGE_COUNT_LIMITATION -> {
                    showToast("이미지는 최대 ${imagePickerViewModel.getImageLimitationCount()}장까지 가져올 수 있습니다")
                }
                else -> {

                }
            }
        }

        imagePickerViewModel.selectedImageItemList.observe(binding.root.context as LifecycleOwner) {
            selectedImageAdapter.submitList(it.toMutableList())
            val selectedImageListSize = it?.size?:0
            if(selectedImageListSize > 0) {
                binding.selectedImageLayout.visibility = View.VISIBLE
            } else {
                binding.selectedImageLayout.visibility = View.GONE
            }
//            val a = imagePickerViewModel.selectedImageItemList.value?.size?:0
//            if(a > 0) {
//                binding.rvCurrentSelectionImage.scrollToPosition((imagePickerViewModel.selectedImageItemList.value?.size?:0) - 1)
//            }
        }

        imagePickerViewModel.imageItemList.observe(binding.root.context as LifecycleOwner) { imageItemList ->
            imagePickerAdapter.submitList(imageItemList.toMutableList())
            imagePickerAdapter.notifyDataSetChanged()
        }
    }

}