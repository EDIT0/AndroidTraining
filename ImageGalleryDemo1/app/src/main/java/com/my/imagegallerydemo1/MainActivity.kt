package com.my.imagegallerydemo1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.my.imagegallerydemo1.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainVM.initPermissionManager(this@MainActivity)

        lifecycleScope.launch {
            mainVM.event
                .collect {
                    when(it.name) {
                        MainViewModel.MyEvent.MoveToSettings.name -> {
                            LogUtil.d_dev("셋팅페이지로 이동")
                            mainVM.navigateToPermissionSettingPage()
                        }
                        MainViewModel.MyEvent.RetryRequest.name -> {
                            LogUtil.d_dev("재요청")
                            mainVM.requestPermissions()
                        }
                        MainViewModel.MyEvent.AllPass.name -> {
                            LogUtil.d_dev("통과")
                        }
                    }
                }
        }

        mainVM.requestPermissions()

        binding.layoutOpenCamera.setOnClickListener {
            mainVM.openCamera(this@MainActivity)
        }

        binding.layoutOpenGallery.setOnClickListener {
            mainVM.openGallery(this@MainActivity)
        }

        binding.layoutOpenMultiGallery.setOnClickListener {
            mainVM.openMultiGallery(this@MainActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        LogUtil.d_dev("onActivityResult\nrequestCode: ${requestCode}\nresultCode: ${resultCode}\ndata: ${data}")

        if(resultCode != Activity.RESULT_OK) {
            LogUtil.d_dev("No RESULT_OK")
            // 이미지 필요없으면 삭제해주기
            return
        }

        when(requestCode) {
            GalleryManager.FLAG_REQ_CAMERA -> {
                if (mainVM.photoURI != null) {
                    LogUtil.i_dev("이미지 URI: ${mainVM.photoURI}")

                    /**
                     * 카메라를 열 때 넘겼던 uri가 photoURI 이므로 사진 찍은 결과가 들어있음
                     */

                    /**
                     * 사진 각도 맞춤
                     * Uri를 수정(리사이즈 등)하기 전에 회전되어 있는 이미지를 꼭 맞춰줘야한다.
                     * */
                    if(mainVM.photoURI == null) {
                        return
                    } else {
                        // 사진 찍은 uri를 bitmap으로 변환
                        val bitmap = Utility.loadBitmapFromMediaStoreBy(this@MainActivity, mainVM.photoURI!!)

                        // 각도 조절한 이미지 bitmap
                        val orientation = Utility.getOrientation(this@MainActivity, mainVM.photoURI!!)
                        val bmRotated = Utility.rotateBitmap(bitmap!!, orientation!!)
                        // 파일 경로 얻기
                        val originalImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.photoURI!!)
                        LogUtil.d_dev("각도 맞추기 전 imagePath: ${originalImagePath}")
                        // 각도 맞추기 전의 사진 삭제
                        mainVM.deleteCameraImage(originalImagePath, arrayListOf())

                        // 각도 맞춘 후 사진을 bitmap에서 uri로 변경 후 photoURI에 저장
                        mainVM.setPhotoURI(Utility.getBitmapToUri(this@MainActivity, bmRotated!!))

                        // 파일 경로 얻기
                        val rotatedImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.photoURI!!)
                        LogUtil.d_dev("각도 맞춘 후 imagePath: ${rotatedImagePath}")

                        // 리사이즈된 이미지 bitmap
                        val resizedBitmap = Utility.resize(this@MainActivity, mainVM.photoURI!!, 500)
                        // 각도 맞춘 후의 사진 삭제
                        mainVM.deleteCameraImage(rotatedImagePath, arrayListOf())
                        // 리사이즈된 사진을 bitmap에서 uri로 변경 후 photoURI에 저장
                        mainVM.setPhotoURI(Utility.getBitmapToUri(this@MainActivity, resizedBitmap!!))

                        // 파일 경로 얻기
                        val resizedImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.photoURI!!)
                        LogUtil.d_dev("리사이즈된 imagePath: ${resizedImagePath}")

                        binding.ivImage.setImageURI(Uri.parse(resizedImagePath))
                        // 이미지 사용 후 카메라 찍은 이미지 삭제
//                        mainVM.deleteCameraImage(resizedImagePath, arrayListOf())
                    }
                }
            }
            GalleryManager.FLAG_REQ_GALLERY -> {
                data?:return // 선택한 이미지가 null이면 종료

                // 갤러리에서 가져온 이미지 uri 저장
                mainVM.setGalleryPhotoURI(data.data as Uri)

                if(mainVM.galleryPhotoURI == null) {
                    return
                } else {
                    // 갤러리에서 가져온 이미지를 복사하여 새로운 bitmap 생성
                    val copyBitmap = Utility.loadBitmapFromMediaStoreBy(this@MainActivity, mainVM.galleryPhotoURI!!)
                    // 카피된 bitmap을 uri로 변경 후 galleryPhotoURI에 저장
                    mainVM.setGalleryPhotoURI(Utility.getBitmapToUri(this@MainActivity, copyBitmap!!))

                    // 파일 경로 얻기
                    val copyImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.galleryPhotoURI!!)
                    LogUtil.d_dev("한장 갤러리 카피된 imagePath: ${copyImagePath}")

                    // 카피된 이미지를 리사이즈한 bitmap 얻기
                    val resizedBitmap = Utility.resize(this@MainActivity, mainVM.galleryPhotoURI!!, 500)
                    // 카피된 사진 삭제
                    mainVM.deleteCameraImage(copyImagePath, arrayListOf())
                    // 리사이즈된 bitmap을 uri로 변경 후 galleryPhotoURI에 저장
                    mainVM.setGalleryPhotoURI(Utility.getBitmapToUri(this@MainActivity, resizedBitmap!!))

                    // 파일 경로 얻기
                    val resizedImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.galleryPhotoURI!!)
                    LogUtil.d_dev("한장 갤러리 리사이즈된 imagePath: ${resizedImagePath}")

                    binding.ivImage.setImageURI(Uri.parse(resizedImagePath))
                }
            }
            GalleryManager.FLAG_REQ_MULTI_GALLERY -> {
                data?:return

                val itemCount = data.clipData?.itemCount?:-1
                LogUtil.d_dev("여러장 갤러리 itemCount: ${itemCount}")
                if(itemCount == -1) {
                    return
                }

                val originalUriList = ArrayList<Uri>()
                for(i in 0 until data.clipData?.itemCount!!) {
                    LogUtil.d_dev("여러장 갤러리 ${data.clipData?.getItemAt(i)}")
                    originalUriList.add(data.clipData?.getItemAt(i)!!.uri)
                }

                val copyImagePathList = ArrayList<String>()
                for(i in 0 until originalUriList.size) {
                    val copyBitmap = Utility.loadBitmapFromMediaStoreBy(this@MainActivity, originalUriList[i])
                    val copyUri = Utility.getBitmapToUri(this@MainActivity, copyBitmap!!) // bitmap에서 uri로 변경
                    mainVM.galleryPhotoURIArrayList.add(copyUri)

                    val copyImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.galleryPhotoURIArrayList[i]) // 파일 경로 얻기
                    copyImagePathList.add(copyImagePath)
                    LogUtil.d_dev("여러장 갤러리 카피된 imagePath: ${copyImagePath}")
                }

                val copyUriList = ArrayList<Uri>()
                copyUriList.addAll(mainVM.galleryPhotoURIArrayList)
                mainVM.galleryPhotoURIArrayList.clear()
                val resizedImagePathList = ArrayList<String>()
                for(i in 0 until copyUriList.size) {
                    val resizedBitmap = Utility.resize(this@MainActivity, copyUriList[i], 500)
                    mainVM.deleteCameraImage(copyImagePathList[i], arrayListOf()) // 카피된 사진 삭제
                    val resizedUri = Utility.getBitmapToUri(this@MainActivity, resizedBitmap!!)
                    mainVM.galleryPhotoURIArrayList.add(resizedUri) // bitmap에서 uri로 변경

                    val resizedImagePath = Utility.absolutelyPath(this@MainActivity, mainVM.galleryPhotoURIArrayList[i]) // 파일 경로 얻기
                    resizedImagePathList.add(resizedImagePath)
                    LogUtil.d_dev("여러장 갤러리 리사이즈된 imagePath: ${resizedImagePath}")
                }
            }
        }
    }
}