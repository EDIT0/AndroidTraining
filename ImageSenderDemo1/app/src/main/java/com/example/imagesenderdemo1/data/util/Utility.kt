package com.example.imagesenderdemo1.data.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import com.theartofdev.edmodo.cropper.CropImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.collections.ArrayList

object Utility {

    val FLAG_REQ_CAMERA = 200 // requestCode
    val FLAG_REQ_CAMERA_CROP = 201 // requestCode
    val FLAG_REQ_GALLERY = 100 // requestCode
    val FLAG_REQ_GALLERY_CROP = 101 // requestCode
    val FLAG_REQ_MULTI_GALLERY = 300 // requestCode
    val FLAG_REQ_MULTI_GALLERY_CROP = 301 // requestCode
    var photoURI: Uri? = null // 카메라 원본이미지 Uri를 저장할 변수
    var photoURIArray: ArrayList<Uri> = ArrayList<Uri>() // 1장 이상 선택 시 이미지 Uri 저장할 변수

    fun openCamera(activity: Activity) {
        // 카메라 인텐트 생성
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        createImageUri(activity, "PictureCameraImageFolder"+ System.currentTimeMillis(), "image/jpeg")?.let { uri ->
            photoURI = uri
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            Log.i(activity.localClassName, "카메라 이미지 uri ${photoURI}")
            activity.startActivityForResult(takePictureIntent, FLAG_REQ_CAMERA)
        }
    }

    fun openGallery(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(intent, FLAG_REQ_GALLERY)
    }

    fun openMultiGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = MediaStore.Images.Media.CONTENT_TYPE
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        activity.startActivityForResult(intent, FLAG_REQ_MULTI_GALLERY)
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
        activity.startActivityForResult(Intent.createChooser(intent,"다중 선택은 '포토'를 선택하세요."), FLAG_REQ_MULTI_GALLERY)
    }

    fun cropImage(activity: Activity, uri: Uri) {
        try {
            CropImage.activity(uri)
                .setAutoZoomEnabled(false)
                .setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                .start(activity)
        } catch (e: Exception) {

        }
    }

    /**
     * @param context
     * */
    fun clearCroppedCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteCroppedFiles(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Helper function to delete all files in a directory if they are named cropped
    private fun deleteCroppedFiles(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (i in children.indices) {
                    deleteCroppedFiles(File(dir, children[i]))
                }
            }
            if (dir.isDirectory && dir.length() == 0L) {
                dir.delete()
            } else {
                false
            }
        } else if (dir != null && dir.isFile) {
            Log.i("CropImage", "제거 ${Regex("(cropped)\\d+.jpg")}")
            if (dir.toString().contains("cropped")) {
                dir.delete()
            } else {
                false
            }
        } else {
            false
        }
    }

    // 1장
    fun multipartBody(file: File, fileName: String) : MultipartBody.Part {
//        var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/jpg"),file)
//        var requestBody : RequestBody = RequestBody.create("image/jpg".toMediaTypeOrNull(),file)
        var requestBody : RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
        return body
    }

    // 1장 이상
    fun multipartArrayBody(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : ArrayList<MultipartBody.Part> {
        var requestBodyArray : ArrayList<RequestBody> = ArrayList<RequestBody>()
        for(i in 0 until fileArray.size) {
            requestBodyArray.add(RequestBody.create("image/jpg".toMediaTypeOrNull(), fileArray.get(i)))
        }
        var bodyArray : ArrayList<MultipartBody.Part> = ArrayList<MultipartBody.Part>()
        for(i in 0 until fileArray.size) {
            bodyArray.add(MultipartBody.Part.createFormData("uploaded_file${i}", fileNameArray.get(i), requestBodyArray.get(i)))
        }
        return bodyArray
    }

    // Camera
    fun createImageUri(activity: Activity, filename: String, mimeType: String) : Uri? {
        var values = ContentValues()
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
//        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    // uri 절대 경로
    fun absolutelyPath(activity: Activity, path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = activity.contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    // Uri To Bitmap
    fun loadBitmapFromMediaStoreBy(activity: Activity, photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source = ImageDecoder.createSource(activity.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(activity.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    // Bitmap To Uri
    fun getBitmapToUri(activity: Activity, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(activity.contentResolver, inImage, "IMG_" + System.currentTimeMillis(), null)
        return Uri.parse(path)
    }

    // 사진 찍기 후 각도 맞추기
    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return try {
            val bmRotated =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            bmRotated
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }

    fun resize(context: Context, uri: Uri, resize: Int): Bitmap? {
        var resizeBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        try {
            BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(uri),
                null,
                options
            ) // 1번
            var width = options.outWidth
            var height = options.outHeight
            var samplesize = 1
            while (true) { //2번
                if (width / 2 < resize || height / 2 < resize) break
                width /= 2
                height /= 2
                samplesize *= 2
            }
            options.inSampleSize = samplesize
            val bitmap = BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(uri),
                null,
                options
            ) //3번
            resizeBitmap = bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return resizeBitmap
    }

    fun fileUriToContentUri(context: Context, file: File): Uri? {

        //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri
        val cr = context.contentResolver
        val imagePath = file.absolutePath
        val imageName: String? = null
        val imageDescription: String? = null
        val uriString =
            MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription)
        return Uri.parse(uriString)
    }
}