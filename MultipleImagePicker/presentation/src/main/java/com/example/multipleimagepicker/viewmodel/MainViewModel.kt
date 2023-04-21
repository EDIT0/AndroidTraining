package com.example.multipleimagepicker.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.*
import com.example.data.util.*
import com.example.domain.model.ImagePickerModel
import com.example.domain.usecase.SaveSelectedImagesToServerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.InputStream

class MainViewModel(
    private val app: Application,
    private val networkManager: NetworkManager,
    private val saveSelectedImagesToServerUseCase: SaveSelectedImagesToServerUseCase
) : AndroidViewModel(app) {

    private val _eventObserver = SingleLiveEvent<String>()
    val eventObserver: LiveData<String> get() = _eventObserver

    private val _selectedImageItemList = MutableLiveData<MutableList<ImagePickerModel>>(mutableListOf())
    val selectedImageItemList: LiveData<MutableList<ImagePickerModel>> = _selectedImageItemList

    var exif: ExifInterface? = null
    var photoURIList: ArrayList<Uri> = ArrayList()
    var imagePathList = ArrayList<String>()
    lateinit var deleteFile: File

    var bitmapList: ArrayList<Bitmap> = ArrayList()
    var imageFileNameList = ArrayList<String>()

    // 미디어 데이터베이스에서 해당 파일 정보 삭제
    val resolver = app.contentResolver
    val deleteUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val selection = MediaStore.Images.Media.DATA + "=?"

    lateinit var fileArray: ArrayList<File>
    lateinit var fileNameArray: ArrayList<String>

    fun setSelectedImageItemList(list: ArrayList<ImagePickerModel>) {
        _selectedImageItemList.value = list.toMutableList()
    }

    /**
     * 선택된 이미지 취소
     * */
    fun setCheckedForSelectedImage(position: Int) {
        var currentSelectedImageList = _selectedImageItemList.value
        var item: ImagePickerModel? = currentSelectedImageList?.get(position)
        if (item != null) {
            currentSelectedImageList?.remove(item)
        }

        _selectedImageItemList.value = currentSelectedImageList as ArrayList
    }

    /**
     * 서버로 이미지 저장하기
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    fun saveSelectedImagesToServer() {
        /**
         * 네트워크 연결 확인
         * */
        if(!networkManager.checkNetworkState()) {
            _eventObserver.value = MessageSet.NETWORK_CONNECTION_ERROR
            return
        }

        /**
         * 선택된 이미지 리스트 확인
         * */
        if((_selectedImageItemList.value?.size?:0) == 0) {
            _eventObserver.value = MessageSet.EMPTY_SELECTED_IMAGES
            return
        }

        for(i in 0 until _selectedImageItemList.value?.size!!) {
            photoURIList.add(_selectedImageItemList.value?.get(i)?.uri!!)
            Log.i("MYTAG", "완성된 전 uri ${_selectedImageItemList.value?.get(i)?.uri!!}")
        }

        viewModelScope.launch(Dispatchers.IO) {

            withContext(Dispatchers.Main) {
                _eventObserver.value = MessageSet.LOADING_START
            }

            withContext(Dispatchers.Default) {
                /**
                 * 사진 각도 맞춤
                 * Uri를 수정(리사이즈 등)하기 전에 회전되어 있는 이미지를 꼭 맞춰줘야한다.
                 * */
                for(i in 0 until photoURIList.size) {
                    val bitmap = Utility.loadBitmapFromMediaStoreBy(app, photoURIList[i])
                    try {
                        val aaa: InputStream = app.contentResolver.openInputStream(photoURIList[i])!!
                        exif = ExifInterface(aaa)
                        aaa.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    val orientation = exif!!.getAttributeInt(
                        ExifInterface.ORIENTATION_ROTATE_90.toString(),
                        ExifInterface.ORIENTATION_UNDEFINED
                    )
                    bitmapList.add(Utility.rotateBitmapInternalStorage(bitmap!!, orientation)!!)
                    val fileName = "${System.currentTimeMillis()}.png"
                    imageFileNameList.add(fileName)
                    photoURIList[i] = Utility.bitmapToUriInternalStorage(app, bitmapList[i], fileName) // bitmap에서 uri로 변경
                }

                // 가져온 이미지 해상도 리사이즈 후 만들기
                for(i in 0 until photoURIList.size) {
                    Log.i("MYTAG", "완성된 uri ${photoURIList[i]}")

                    bitmapList[i] = Utility.resize(app, photoURIList[i], 500)!!
//                Utility.saveBitmapToInternalStorage(app, bitmapList[i])

                    photoURIList[i] = Utility.bitmapToUriInternalStorage(app, bitmapList[i], imageFileNameList[i]) // bitmap에서 uri로 변경
                }

                for(i in 0 until photoURIList.size) {
                    val file: File = File("${app.filesDir}/PickerImage", imageFileNameList[i]) // 파일 경로 설정
                    val filePath = file.absolutePath // 파일 경로 얻기
                    imagePathList.add(filePath)
                }

                fileArray = ArrayList<File>()
                fileNameArray = ArrayList<String>()
                for(i in 0 until _selectedImageItemList.value?.size!!) {
                    Log.i("MYTAG", "ImagePathList : ${imagePathList}")
                    fileArray.add(File(imagePathList[i]))
                    fileNameArray.add("all${i}")
                }
            }



            val apiResult = saveSelectedImagesToServerUseCase.execute(fileArray, fileNameArray)
            apiResult
                .onStart {
                    Log.i("MYTAG", "saveImage onStart")
                    withContext(Dispatchers.Main) {
                        _eventObserver.value = MessageSet.LOADING_START
                    }
                }
                .onCompletion {
                    Log.i("MYTAG", "saveImage onCompletion")
                    withContext(Dispatchers.Main) {
                        _eventObserver.value = MessageSet.LOADING_END
                    }
                }
                .catch { e ->
                    when (e.message) {
                        MessageSet.ERROR -> {
                            Log.i("MYTAG", "Error ${e.message}")
                        }
                        else -> {
                            Log.i("MYTAG", "??? ${e.message}")
                        }
                    }
                    withContext(Dispatchers.Main) {
                        Utility.clearCroppedCache(app)
                        selectedImagesDelete()
                        _eventObserver.value = MessageSet.ERROR
                    }
                }
                .collect {
                    Log.i("MYTAG", "이미지 저장: ${it}")
                    withContext(Dispatchers.Main) {
                        Utility.clearCroppedCache(app)
                        selectedImagesDelete()
                        _eventObserver.value = MessageSet.SUCCESS
                    }
                }
        }






//        /**
//         * 사진 각도 맞춤
//         * Uri를 수정(리사이즈 등)하기 전에 회전되어 있는 이미지를 꼭 맞춰줘야한다.
//         * */
//        for(i in 0 until _selectedImageItemList.value?.size!!) {
//            val bitmap = Utility.loadBitmapFromMediaStoreBy(app, photoURIList[i])
//            Log.i("MYTAG", "이미지 비트맵: ${bitmap}")
//
//            /**
//             * val bitmap = Utility.loadBitmapFromMediaStoreBy(app, photoURIList[i])
//             * 이 부분에서 만든 Bitmap 파일을 제거하기 위해 imagePath를 받아놓는다.
//             * */
//            var imagePath = ""
//            if(photoURIList[i].toString().contains("file://")) {
//                val contentUri = Utility.fileUriToContentUri(app, File(photoURIList[i].getPath()))
//                imagePath = Utility.absolutelyPath(app, contentUri!!) // 파일 경로 얻기
//            } else if(photoURIList[i].toString().contains("content://")) {
//                imagePath = Utility.absolutelyPath(app, photoURIList[i]) // 파일 경로 얻기
//            }
////            val contentUri = Utility.fileUriToContentUri(app, File(photoURIList[i].getPath()))
////            val imagePath = Utility.absolutelyPath(app, contentUri!!) // 파일 경로 얻기
//
//            try {
//                val aaa: InputStream = app.contentResolver.openInputStream(photoURIList[i])!!
//                exif = ExifInterface(aaa)
//                aaa.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            val orientation = exif!!.getAttributeInt(
//                ExifInterface.ORIENTATION_ROTATE_90.toString(),
//                ExifInterface.ORIENTATION_UNDEFINED
//            )
//            val bmRotated: Bitmap = Utility.rotateBitmap(bitmap!!, orientation)!!
////            val bmRotated: Bitmap = Utility.rotateImage(app, photoURIList[i], bitmap!!)
//            photoURIList[i] = Utility.getBitmapToUri(app, bmRotated) // bitmap에서 uri로 변경
//
//            /**
//             * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
//             * */
//            deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
//            deleteFile.delete()
//
//            val selectionArgs = arrayOf(imagePath)
//            resolver.delete(deleteUri, selection, selectionArgs)
//            // 삭제된 파일이 앨범 등에서 보이지 않도록 미디어 스캐닝
//            MediaScannerConnection.scanFile(app, arrayOf(imagePath), null, null)
//        }
//
//        // 가져온 이미지 해상도 리사이즈 후 만들기
//        for(i in 0 until _selectedImageItemList.value?.size!!) {
//            /**
//             * photoURIList[i] = Utility.getBitmapToUri(app, bmRotated)
//             * 이 부분에서 만든 Bitmap 파일을 제거하기 위해 imagePath를 받아놓는다.
//             * */
//            val imagePath = Utility.absolutelyPath(app, photoURIList[i]) // 파일 경로 얻기
//            val resizeBitmap = Utility.resize(app, photoURIList[i], 500)
//            photoURIList[i] = Utility.getBitmapToUri(app, resizeBitmap!!)
//            Log.i("MYTAG", "완성된 uri ${photoURIList[i]}")
//
//            /**
//             * getBitmapToUri로 새로운 Bitmap을 생성하였으므로 기존에 Bitmap 파일을 imagePath를 이용하여 지워준다.
//             * */
//            deleteFile = File(imagePath) // 1. 사진 찍으면 생성된 이미지 파일 삭제
//            deleteFile.delete()
//            val selectionArgs = arrayOf(imagePath)
//            resolver.delete(deleteUri, selection, selectionArgs)
//            // 삭제된 파일이 앨범 등에서 보이지 않도록 미디어 스캐닝
//            MediaScannerConnection.scanFile(app, arrayOf(imagePath), null, null)
//        }
//
//        for(i in 0 until _selectedImageItemList.value?.size!!) {
//            imagePathList.add(Utility.absolutelyPath(app, photoURIList[i]))
//        }
//
//        val fileArray = ArrayList<File>()
//        val fileNameArray = ArrayList<String>()
//        for(i in 0 until _selectedImageItemList.value?.size!!) {
//            fileArray.add(File(imagePathList[i]))
//            fileNameArray.add("all${i}")
//        }

//        viewModelScope.launch(Dispatchers.IO) {
//            val apiResult = saveSelectedImagesToServerUseCase.execute(fileArray, fileNameArray)
//            apiResult
//                .onStart {
//                    Log.i("MYTAG", "saveImage onStart")
//                    withContext(Dispatchers.Main) {
//                        _eventObserver.value = MessageSet.LOADING_START
//                    }
//                }
//                .onCompletion {
//                    Log.i("MYTAG", "saveImage onCompletion")
//                    withContext(Dispatchers.Main) {
//                        _eventObserver.value = MessageSet.LOADING_END
//                    }
//                }
//                .catch { e ->
//                    when (e.message) {
//                        MessageSet.ERROR -> {
//                            Log.i("MYTAG", "Error ${e.message}")
//                        }
//                        else -> {
//                            Log.i("MYTAG", "??? ${e.message}")
//                        }
//                    }
//                    withContext(Dispatchers.Main) {
//                        Utility.clearCroppedCache(app)
//                        selectedImagesDelete()
//                        _eventObserver.value = MessageSet.ERROR
//                    }
//                }
//                .collect {
//                    Log.i("MYTAG", "이미지 저장: ${it}")
//                    withContext(Dispatchers.Main) {
//                        Utility.clearCroppedCache(app)
//                        selectedImagesDelete()
//                        _eventObserver.value = MessageSet.SUCCESS
//                    }
//                }
//        }

    }

    private fun selectedImagesDelete() {
//        for(i in 0 until imagePathList.size) {
//            Log.i("MYTAG", "삭제${i}")
//            deleteFile = File(imagePathList[i])
//            deleteFile.delete()
//            val selectionArgs = arrayOf(imagePathList[i])
//            resolver.delete(deleteUri, selection, selectionArgs)
//            // 삭제된 파일이 앨범 등에서 보이지 않도록 미디어 스캐닝
//            MediaScannerConnection.scanFile(app, arrayOf(imagePathList[i]), null, null)
//        }
//        imagePathList.clear()
//        photoURIList.clear()
//        setSelectedImageItemList(mutableListOf<ImagePickerModel>() as ArrayList<ImagePickerModel>)

        val dirPath: String = "${app.filesDir}/PickerImage"
        Utility.setDirEmpty(dirPath)
        imagePathList.clear()
        photoURIList.clear()
        bitmapList.clear()
        setSelectedImageItemList(mutableListOf<ImagePickerModel>() as ArrayList<ImagePickerModel>)
    }








    // 각도 알아보기 코드

//    var photoRotation = 0f
//    var hasRotation = false
//    var projection = arrayOf<String>(MediaStore.Images.ImageColumns.ORIENTATION)
//
//    fun solution1(photoUri: Uri) {
//        try {
//            val cursor: Cursor? = app.contentResolver.query(photoUri, projection, null, null, null)
//            if (cursor!!.moveToFirst()) {
//                photoRotation = cursor.getInt(0).toFloat()
//                hasRotation = true
//            }
//            cursor.close()
//        } catch (e: Exception) {
//        }
//        Log.i("MYTAG", "리얼 각도1: ${hasRotation}")
//    }
//
//    fun solution2(photoUri: Uri) {
//        if (!hasRotation) {
//            val exif: ExifInterface? = photoUri.getPath()?.let { ExifInterface(it) }
//            val exifRotation = exif?.getAttributeInt(
//                ExifInterface.TAG_ORIENTATION,
//                ExifInterface.ORIENTATION_UNDEFINED
//            )
//            when (exifRotation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> {
//                    photoRotation = 90.0f
//                }
//                ExifInterface.ORIENTATION_ROTATE_180 -> {
//                    photoRotation = 180.0f
//                }
//                ExifInterface.ORIENTATION_ROTATE_270 -> {
//                    photoRotation = 270.0f
//                }
//            }
//
//            Log.i("MYTAG", "리얼 각도: ${photoRotation}")
//        }
//    }
}