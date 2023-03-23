package com.example.multipleimagepicker.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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

    @Inject
    lateinit var imagePickerViewModelFactory: ImagePickerViewModelFactory
    lateinit var imagePickerViewModel: ImagePickerViewModel

    private val selectedImageAdapter = SelectedImageAdapter()
    private val imagePickerAdapter = ImagePickerAdapter()

    override fun getBinding(): ActivityMultipleImagePickerBinding = ActivityMultipleImagePickerBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerViewModel = ViewModelProvider(this, imagePickerViewModelFactory)[ImagePickerViewModel::class.java]

        buttonClickListener()
        currentSelectedImageRVSetting()
        imagePickerRVSetting()
        observe()
        albumDataSetting()
    }

    private val returnStartIntent : (activity: Class<*>) -> Intent = { activity ->
        val intent = Intent(binding.root.context, activity)
        if(activity == ImageDecoViewPagerActivity::class.java) {
            intent.putParcelableArrayListExtra("uris", imagePickerViewModel.selectedImageItemList.value as ArrayList<ImagePickerModel>)
        }
        intent
    }

    private fun buttonClickListener() {
        binding.ibClose.setOnClickListener {
            onBackPressed()
        }

        binding.btnEdit.setOnClickListener {
            launcher.launch(returnStartIntent(ImageDecoViewPagerActivity::class.java))
        }

        binding.btnComplete.setOnClickListener {
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
                binding.btnEdit.visibility = View.VISIBLE
                binding.btnComplete.visibility = View.VISIBLE
            } else {
                binding.selectedImageLayout.visibility = View.GONE
                binding.btnEdit.visibility = View.GONE
                binding.btnComplete.visibility = View.GONE
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

    private fun albumDataSetting() {
        imagePickerViewModel.setImageLimitationCount(7)
        imagePickerViewModel.getAlbumImageList()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent = result.data!!
            // RESULT_OK일 때 실행할 코드...
//            try {
//                mainViewModel.setSelectedImageItemList(result.data?.getParcelableArrayListExtra<ImagePickerModel>("uris")!!)
//                Log.i("MYTAG", "넘어온 이미지 갯수: ${mainViewModel.selectedImageItemList.value?.size}")
//            } catch (e : Exception) {
//                Log.i("MYTAG", "${e.message}")
//            }

            val intent = Intent(binding.root.context, MainActivity::class.java).apply {
                putParcelableArrayListExtra("uris", result.data?.getParcelableArrayListExtra<ImagePickerModel>("uris")!! as ArrayList)
            }
            setResult(RESULT_OK, intent)
            onBackPressed()

            Log.i("MYTAG", "${result}")
        }
    }

}