package com.example.imagesenderdemo1.presentation.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.util.DELETE_IMAGE_COMPLETE
import com.example.imagesenderdemo1.data.util.EventBus
import com.example.imagesenderdemo1.data.util.Utility
import com.example.imagesenderdemo1.databinding.ActivityMainBinding
import com.example.imagesenderdemo1.presentation.base.DataBindingBaseActivity
import com.example.imagesenderdemo1.presentation.ui.fragment.PickImageFragment
import com.example.imagesenderdemo1.presentation.ui.fragment.SavedImageFragment
import com.example.imagesenderdemo1.presentation.viewmodel.MainViewModel
import com.example.imagesenderdemo1.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : DataBindingBaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var pickImageFragment: PickImageFragment
    private lateinit var savedImageFragment: SavedImageFragment

    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)

        userPermission()

        EventBus.registerBus(mActivity)
    }

    private fun userPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13 이상
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("제목")
                .setDeniedMessage("메시지")
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
                .setDeniedCloseButtonText("닫기")
                .setGotoSettingButtonText("설정으로 이동")
                .check()
        } else {
            // Android 13 미만
            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("제목")
                .setDeniedMessage("메시지")
                .setPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .setDeniedCloseButtonText("닫기")
                .setGotoSettingButtonText("설정으로 이동")
                .check()
        }
    }

    private fun initFragment() {
        pickImageFragment = PickImageFragment()
        savedImageFragment = SavedImageFragment()
    }

    private fun viewPagerAndTabLayoutSetting() {
        binding.vpMain.offscreenPageLimit = 2
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addItemfragment(pickImageFragment)
        viewPagerAdapter.addItemfragment(savedImageFragment)

        binding.vpMain.adapter = viewPagerAdapter

        val tabPickImageFragment = binding.tabLayout.newTab().setText("이미지 불러오기")
        binding.tabLayout.addTab(tabPickImageFragment)
        val tabSavedImageFragment = binding.tabLayout.newTab().setText("저장된 이미지")
        binding.tabLayout.addTab(tabSavedImageFragment)

        binding.vpMain.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 선택되지 않았던 탭이 선택되었을 경우
                binding.vpMain.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택된 상태에서 선택되지 않음으로 변경될 경우
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택되었을 경우
            }

        })
    }

    private fun observer() {
        mainViewModel.isSuccess.observe(mActivity as LifecycleOwner) {
            // 성공하든 안하든 이미지 삭제
            deleteFile = File(imagePath)
            deleteFile.delete()
            imagePath = ""
            Utility.photoURI = null
            for(i in 0 until imagePathArray.size) {
                Log.i(pickImageFragment.TAG, "삭제${i}")
                deleteFile = File(imagePathArray.get(i))
                deleteFile.delete()
            }
            imagePathArray.clear()
            Utility.photoURIArray.clear()

            mainViewModel.init()
            mainViewModel.selectTotalSavedImage("Test")
            showToast("이미지 업로드 완료")
        }
    }

    inner class ViewPagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        var items: ArrayList<Fragment> = ArrayList<Fragment>() //프래그먼트 화면을 담아둘 배열


        //ArrayList에 add로 화면 추가하는 메소드
        fun addItemfragment(item: Fragment) {
            items.add(item)
        }

        override fun getCount(): Int = items.size


        override fun getItem(position: Int): Fragment = items.get(position)
    }

    var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            showToast("Permission Granted")
            initFragment()
            viewPagerAndTabLayoutSetting()
            observer()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            showToast("Permission Denied\n" + "$deniedPermissions")
        }
    }


    var exif: ExifInterface? = null
    lateinit var deleteFile: File
    private var imagePath = ""
    private var imagePathArray = ArrayList<String>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(pickImageFragment.TAG, "onActivityResult() ${requestCode} / ${resultCode} / ${data}")

        if(resultCode != Activity.RESULT_OK) {
            Log.i(pickImageFragment.TAG, "not RESULT_OK")
            return
        }

        when(requestCode) {
            Utility.FLAG_REQ_GALLERY -> {
                data?:return
                Utility.photoURI = data.data as Uri

                /**
                 * 사진 각도 맞춤
                 * Uri를 수정(리사이즈 등)하기 전에 회전되어 있는 이미지를 꼭 맞춰줘야한다.
                 * */
                val bitmap = Utility.loadBitmapFromMediaStoreBy(mActivity, Utility.photoURI!!)
                Log.i("MYTAG", "이미지 비트맵: ${bitmap}")

                /**
                 * val bitmap = Utility.loadBitmapFromMediaStoreBy(app, photoURIList[i])
                 * 이 부분에서 만든 Bitmap 파일을 제거하기 위해 imagePath를 받아놓는다.
                 * */
//                    val contentUri = Utility.fileUriToContentUri(app, File(photoURIList[i].getPath()))
                imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!) // 파일 경로 얻기

                try {
                    val aaa: InputStream = mActivity.contentResolver.openInputStream(Utility.photoURI!!)!!
                    exif = ExifInterface(aaa)
                    aaa.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val orientation = exif!!.getAttributeInt(
                    ExifInterface.ORIENTATION_ROTATE_90.toString(),
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                val bmRotated: Bitmap = Utility.rotateBitmap(bitmap!!, orientation)!!
//            val bmRotated: Bitmap = Utility.rotateImage(app, photoURIList[i], bitmap!!)
                Utility.photoURI = Utility.getBitmapToUri(mActivity, bmRotated) // bitmap에서 uri로 변경

                /**
                 * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
                 * */
                deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                deleteFile.delete()

                imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!) // 파일 경로 얻기

                Log.i(pickImageFragment.TAG, "Uri: ${Utility.photoURI}")
//                Log.i(pickImageFragment.TAG, "AbsolutelyPath: ${Utility.absolutelyPath(mActivity, Utility.photoURI!!)}")

                // 가져온 이미지 해상도 그대로 만들기
//                val bitmap = loadBitmapFromMediaStoreBy(photoURI!!) // 가져온 이미지 Bitmap으로 전환
//                photoURI = getBitmapToUri(bitmap!!)

                // 가져온 이미지 해상도 리사이즈 후 만들기
                var resizeBitmap = Utility.resize(mActivity, Utility.photoURI!!, 500)
                Utility.photoURI = Utility.getBitmapToUri(mActivity, resizeBitmap!!)
                Log.i(pickImageFragment.TAG, "완성된 uri ${Utility.photoURI}")

                /**
                 * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
                 * */
                deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                deleteFile.delete()

                imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!) // 파일 경로 얻기

                val file = File(imagePath)
                var fileName = "all1"

                mainViewModel.saveImage(file, fileName)
            }
            Utility.FLAG_REQ_CAMERA -> {
                if (Utility.photoURI != null) {
                    Log.i(pickImageFragment.TAG, "이미지 URI: ${Utility.photoURI}")
                    val bitmap = Utility.loadBitmapFromMediaStoreBy(mActivity, Utility.photoURI!!)
                    Log.i(pickImageFragment.TAG, "이미지 비트맵: ${bitmap}")

                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!) // 파일 경로 얻기

                    /*
                    * 사진 각도 맞춤
                    * */
                    try {
                        exif = ExifInterface(imagePath)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val orientation = exif!!.getAttributeInt(ExifInterface.ORIENTATION_ROTATE_90.toString(), ExifInterface.ORIENTATION_UNDEFINED)
                    val bmRotated: Bitmap = Utility.rotateBitmap(bitmap!!, orientation)!!

                    Utility.photoURI = Utility.getBitmapToUri(mActivity, bmRotated) // bitmap에서 uri로 변경
                    deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                    deleteFile.delete()
                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!)
//                        val resizedBmp = Bitmap.createScaledBitmap(bitmap!!, bitmap.width / 5, bitmap.height/5, true)
//                        photoURI = getImageUri(resizedBmp)
                    // 사진 리사이징
                    var resizeBitmap = Utility.resize(mActivity, Utility.photoURI!!, 500)
                    Utility.photoURI = Utility.getBitmapToUri(mActivity, resizeBitmap!!)
                    deleteFile = File(imagePath) // 2. 각도 조절한 이미지 파일 삭제
                    deleteFile.delete()
                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!)

                    Log.i(pickImageFragment.TAG, "완성된 uri ${Utility.photoURI}")

                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURI!!) // 파일 경로 얻기

                    val file = File(imagePath)
                    var fileName = "all2"

                    mainViewModel.saveImage(file, fileName)
                }
            }
            Utility.FLAG_REQ_MULTI_GALLERY -> {
                data?:return
//                Utility.photoURI = data.data as Uri
                Log.i(pickImageFragment.TAG, "${data.clipData}")
                Log.i(pickImageFragment.TAG, "${data.clipData?.itemCount}")
                val itemCount = data.clipData?.itemCount?:-1
                if(itemCount == -1) {
                    return
                }
                for(i in 0 until data.clipData?.itemCount!!) {
                    Log.i(pickImageFragment.TAG, "${data.clipData?.getItemAt(i)}")
                    Utility.photoURIArray.add(data.clipData?.getItemAt(i)!!.uri)
                }

                // 가져온 이미지 해상도 그대로 만들기
//                val bitmap = loadBitmapFromMediaStoreBy(photoURI!!) // 가져온 이미지 Bitmap으로 전환
//                photoURI = getBitmapToUri(bitmap!!)

                // 가져온 이미지 해상도 리사이즈 후 만들기
//                for(i in 0 until data.clipData?.itemCount!!) {
//                    val resizeBitmap = Utility.resize(mActivity, Utility.photoURIArray[i], 500)
//                    Utility.photoURIArray[i] = Utility.getBitmapToUri(mActivity, resizeBitmap!!)
//                    Log.i(pickImageFragment.TAG, "완성된 uri ${Utility.photoURIArray[i]}")
//                }

                /**
                 * 사진 각도 맞춤
                 * Uri를 수정(리사이즈 등)하기 전에 회전되어 있는 이미지를 꼭 맞춰줘야한다.
                 * */
                for(i in 0 until data.clipData?.itemCount!!) {
                    val bitmap = Utility.loadBitmapFromMediaStoreBy(mActivity, Utility.photoURIArray[i])
                    Log.i("MYTAG", "이미지 비트맵: ${bitmap}")

                    /**
                     * val bitmap = Utility.loadBitmapFromMediaStoreBy(app, photoURIList[i])
                     * 이 부분에서 만든 Bitmap 파일을 제거하기 위해 imagePath를 받아놓는다.
                     * */
//                    val contentUri = Utility.fileUriToContentUri(app, File(photoURIList[i].getPath()))
                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURIArray[i]) // 파일 경로 얻기

                    try {
                        val aaa: InputStream = mActivity.contentResolver.openInputStream(Utility.photoURIArray[i])!!
                        exif = ExifInterface(aaa)
                        aaa.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val orientation = exif!!.getAttributeInt(
                        ExifInterface.ORIENTATION_ROTATE_90.toString(),
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                    val bmRotated: Bitmap = Utility.rotateBitmap(bitmap!!, orientation)!!
//            val bmRotated: Bitmap = Utility.rotateImage(app, photoURIList[i], bitmap!!)
                    Utility.photoURIArray[i] = Utility.getBitmapToUri(mActivity, bmRotated) // bitmap에서 uri로 변경

                    /**
                     * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
                     * */
                    deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                    deleteFile.delete()
                }

                // 가져온 이미지 해상도 리사이즈 후 만들기
                for(i in 0 until data.clipData?.itemCount!!) {
                    /**
                     * photoURIList[i] = Utility.getBitmapToUri(app, bmRotated)
                     * 이 부분에서 만든 Bitmap 파일을 제거하기 위해 imagePath를 받아놓는다.
                     * */
                    imagePath = Utility.absolutelyPath(mActivity, Utility.photoURIArray[i]) // 파일 경로 얻기
                    val resizeBitmap = Utility.resize(mActivity, Utility.photoURIArray[i], 500)
                    Utility.photoURIArray[i] = Utility.getBitmapToUri(mActivity, resizeBitmap!!)
                    Log.i("MYTAG", "완성된 uri ${Utility.photoURIArray[i]}")

                    /**
                     * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
                     * */
                    deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
                    deleteFile.delete()
                }

                for(i in 0 until data.clipData?.itemCount!!) {
                    imagePathArray.add(Utility.absolutelyPath(mActivity, Utility.photoURIArray[i]))
                }

                val fileArray = ArrayList<File>()
                val fileNameArray = ArrayList<String>()
                for(i in 0 until data.clipData?.itemCount!!) {
                    fileArray.add(File(imagePathArray.get(i)))
                    fileNameArray.add("all${i}")
                }

                mainViewModel.saveImages(fileArray, fileNameArray)
            }
            else -> {
                showToast("불러오기 실패")
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveEventBus(data: String) {
        if(DELETE_IMAGE_COMPLETE == data) {
            mainViewModel.init()
            mainViewModel.selectTotalSavedImage("Test")
        }
    }

    override fun onDestroy() {
        EventBus.unregisterBus(mActivity)
        super.onDestroy()
    }
}