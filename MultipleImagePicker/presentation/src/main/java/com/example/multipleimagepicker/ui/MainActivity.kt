package com.example.multipleimagepicker.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.data.util.MessageSet
import com.example.domain.model.ImagePickerModel
import com.example.multipleimagepicker.adapter.SelectedImageAdapter
import com.example.multipleimagepicker.databinding.ActivityMainBinding
import com.example.multipleimagepicker.ui.base.ViewBindingBaseActivity
import com.example.multipleimagepicker.viewmodel.MainViewModel
import com.example.multipleimagepicker.viewmodel.MainViewModelFactory
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ViewBindingBaseActivity<ActivityMainBinding>() {

//    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var mainViewModel: MainViewModel

    private val selectedImageAdapter = SelectedImageAdapter()

    private val permissionCheck: () -> Unit = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("알림")
                .setDeniedMessage("권한 필요")
                .setPermissions(
                    android.Manifest.permission.READ_MEDIA_IMAGES
                )
                .setDeniedCloseButtonText("종료")
                .setGotoSettingButtonText("설정")
                .check()
        } else {
            // Android 13 미만
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("알림")
                .setDeniedMessage("권한 필요")
                .setPermissions(
                    READ_EXTERNAL_STORAGE
                )
                .setDeniedCloseButtonText("종료")
                .setGotoSettingButtonText("설정")
                .check()
        }
    }

    private val returnStartIntent : (activity: Class<*>) -> Intent = { activity ->
        val intent = Intent(binding.root.context, activity)
        intent
    }

    // launcher 선언
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent = result.data!!
            // RESULT_OK일 때 실행할 코드...
            try {
                mainViewModel.setSelectedImageItemList(result.data?.getParcelableArrayListExtra<ImagePickerModel>("uris")!!)
                Log.i("MYTAG", "넘어온 이미지 갯수: ${mainViewModel.selectedImageItemList.value?.size}")
            } catch (e : Exception) {
                Log.i("MYTAG", "${e.message}")
            }

            Log.i("MYTAG", "${result}")
        }
    }

    override fun getBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        permissionCheck.invoke()

        buttonClickListener()
        selectedImageRVSetting()
        observe()
    }

    private fun buttonClickListener() {
        binding.btnAlbum.setOnClickListener {
            // launcher를 이용해서 화면 시작하기
            launcher.launch(returnStartIntent(MultipleImagePickerActivity::class.java))
        }

        binding.btnSaveToServer.setOnClickListener {
            mainViewModel.saveSelectedImagesToServer()
//            if((mainViewModel.selectedImageItemList.value?.size?:0) == 0) {
//                return@setOnClickListener
//            }

//            for(i in 0 until mainViewModel.selectedImageItemList.value?.size!!) {
//                mainViewModel.photoURIList.add(mainViewModel.selectedImageItemList.value?.get(i)?.uri!!)
//            }
//
//            // 가져온 이미지 해상도 리사이즈 후 만들기
//            for(i in 0 until mainViewModel.selectedImageItemList.value?.size!!) {
//                val resizeBitmap = Utility.resize(mActivity, mainViewModel.photoURIList[i], 500)
//                mainViewModel.photoURIList[i] = Utility.getBitmapToUri(mActivity, resizeBitmap!!)
//                Log.i("MYTAG", "완성된 uri ${mainViewModel.photoURIList[i]}")
//            }
//
//            for(i in 0 until mainViewModel.selectedImageItemList.value?.size!!) {
//                mainViewModel.imagePathList.add(Utility.absolutelyPath(mActivity, mainViewModel.photoURIList[i]))
//            }
//
//            val fileArray = ArrayList<File>()
//            val fileNameArray = ArrayList<String>()
//            for(i in 0 until mainViewModel.selectedImageItemList.value?.size!!) {
//                fileArray.add(File(mainViewModel.imagePathList[i]))
//                fileNameArray.add("all${i}")
//            }

//            mainViewModel.saveImages(fileArray, fileNameArray)
        }
    }

    private fun selectedImageRVSetting() {
        binding.rvCurrentSelectionImage.adapter = selectedImageAdapter

        selectedImageAdapter.setOnItemClickListener { i, imagePickerModel ->
            mainViewModel.setCheckedForSelectedImage(i)
        }
    }

    private fun observe() {
        mainViewModel.eventObserver.observe(binding.root.context as LifecycleOwner) {
            when(it) {
                MessageSet.NETWORK_CONNECTION_ERROR -> {
                    showToast("네트워크 연결을 확인해주세요")
                }
                MessageSet.EMPTY_SELECTED_IMAGES -> {
                    showToast("선택된 이미지가 없습니다")
                }
                MessageSet.ERROR -> {
                    binding.loadingLayout.visibility = View.GONE
                    showToast("이미지 저장 실패")
                }
                MessageSet.SUCCESS -> {
                    binding.loadingLayout.visibility = View.GONE
                    showToast("이미지 저장 성공")
                }
                MessageSet.LOADING_START -> {
                    binding.loadingLayout.visibility = View.VISIBLE
                }
                MessageSet.LOADING_END -> {
                    binding.loadingLayout.visibility = View.GONE
                }
                else -> {

                }
            }
        }

        mainViewModel.selectedImageItemList.observe(binding.root.context as LifecycleOwner) {
            selectedImageAdapter.submitList(it.toMutableList())
            val selectedImageListSize = it?.size?:0
            if(selectedImageListSize > 0) {
                binding.selectedImageLayout.visibility = View.VISIBLE
                binding.btnSaveToServer.visibility = View.VISIBLE
            } else {
                binding.selectedImageLayout.visibility = View.GONE
                binding.btnSaveToServer.visibility = View.GONE
            }
//            val a = imagePickerViewModel.selectedImageItemList.value?.size?:0
//            if(a > 0) {
//                binding.rvCurrentSelectionImage.scrollToPosition((imagePickerViewModel.selectedImageItemList.value?.size?:0) - 1)
//            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(binding.root.context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(binding.root.context, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}