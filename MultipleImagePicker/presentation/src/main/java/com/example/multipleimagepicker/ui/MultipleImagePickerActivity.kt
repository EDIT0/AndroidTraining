package com.example.multipleimagepicker.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.data.util.FLAG_REQ_CAMERA_CROP
import com.example.data.util.MessageSet
import com.example.data.util.Utility
import com.example.domain.model.ImagePickerModel
import com.example.domain.model.ViewType
import com.example.multipleimagepicker.adapter.ImagePickerAdapter
import com.example.multipleimagepicker.adapter.SelectedImageAdapter
import com.example.multipleimagepicker.databinding.ActivityMultipleImagePickerBinding
import com.example.multipleimagepicker.ui.base.ViewBindingBaseActivity
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModel
import com.example.multipleimagepicker.viewmodel.ImagePickerViewModelFactory
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MultipleImagePickerActivity : ViewBindingBaseActivity<ActivityMultipleImagePickerBinding>() {

    @Inject
    lateinit var imagePickerViewModelFactory: ImagePickerViewModelFactory
    lateinit var imagePickerViewModel: ImagePickerViewModel

    private val selectedImageAdapter = SelectedImageAdapter()
    private val imagePickerAdapter = ImagePickerAdapter()

    private lateinit var cameraResultUri: Uri

    lateinit var deleteFile: File
    private var imagePath = ""

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

    // Camera
    private fun createImageUri(activity: Activity, filename: String, mimeType: String) : Uri? {
        var values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    private fun imagePickerRVSetting() {
        binding.rvImage.adapter = imagePickerAdapter

        imagePickerAdapter.setOnItemClickListener { i, imagePickerModel ->
            if(imagePickerModel.type == ViewType.CAMERA) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                createImageUri(mActivity, "PictureCameraImageFolder"+ System.currentTimeMillis(), "image/jpeg")?.let { uri ->
                    cameraResultUri = uri
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraResultUri)
                    Log.i("MYTAG", "카메라 이미지 uri ${cameraResultUri}")
                    cameraLauncher.launch(takePictureIntent)
//                    startActivityForResult(takePictureIntent, FLAG_REQ_CAMERA)
                }
            } else if(imagePickerModel.type == ViewType.ALBUM) {
                imagePickerViewModel.setCheckedForImagePicker(i, !(imagePickerModel.isChecked))
            }
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
            if(imageItemList.isEmpty()) {
                imagePickerViewModel.addCameraItem(imageItemList as ArrayList<ImagePickerModel>)
            } else {
                imagePickerViewModel.apply {
                    if(imageItemList?.get(0)?.uri.toString() != "") {
                        addCameraItem(imageItemList as ArrayList<ImagePickerModel>)
                        return@observe
                    }
                }
            }

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
            val intent = Intent(binding.root.context, MainActivity::class.java).apply {
                putParcelableArrayListExtra("uris", result.data?.getParcelableArrayListExtra<ImagePickerModel>("uris")!! as ArrayList)
            }
            setResult(RESULT_OK, intent)
            onBackPressed()

            Log.i("MYTAG", "${result}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val cameraLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
//            val data: Intent = result.data!!

            CropImage.activity(cameraResultUri)
                .setAutoZoomEnabled(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(mActivity)


            val bitmap = Utility.loadBitmapFromMediaStoreBy(mActivity, cameraResultUri)
            Log.i("MYTAG", "이미지 비트맵: ${bitmap}")

            imagePath = Utility.absolutelyPath(mActivity, cameraResultUri) // 파일 경로 얻기

            Log.i("MYTAG", "${cameraResultUri}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if(resultCode == RESULT_OK) {
                cameraResultUri = result.uri as Uri
                Log.i("MYTAG", "크롭된 이미지 ${result.uri as Uri}")
                cameraResultUri = Utility.fileUriToContentUri(mActivity, File(cameraResultUri.path))!!

                deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                deleteFile.delete()

                // 미디어 데이터베이스에서 해당 파일 정보 삭제
                val resolver = contentResolver
                val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                val selection = MediaStore.Images.Media.DATA + "=?"
                val selectionArgs = arrayOf(imagePath)
                resolver.delete(uri, selection, selectionArgs)
                // 삭제된 파일이 앨범 등에서 보이지 않도록 미디어 스캐닝
                MediaScannerConnection.scanFile(this, arrayOf(imagePath), null, null)

                val intent = Intent(binding.root.context, MainActivity::class.java).apply {
                    val a = ArrayList<ImagePickerModel>()
                    a.add(ImagePickerModel(cameraResultUri, false, ViewType.CAMERA))
                    putParcelableArrayListExtra("uris", a as ArrayList)
                }
                setResult(FLAG_REQ_CAMERA_CROP, intent)
                onBackPressed()


            }
        }
    }
}