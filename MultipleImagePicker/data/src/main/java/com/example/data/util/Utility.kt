package com.example.data.util

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


object Utility {
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

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun rotateImage(context: Context, uri: Uri, bitmap: Bitmap): Bitmap {
//        val aaa: InputStream = context.contentResolver.openInputStream(uri)!!
//        val exif = ExifInterface(aaa)
//        aaa.close()
//
//        val orientation = exif.getAttributeInt(ExifInterface.ORIENTATION_ROTATE_90.toString(), ExifInterface.ORIENTATION_UNDEFINED)
//        val matrix = Matrix()
//        when (orientation) {
//            ExifInterface.ORIENTATION_NORMAL -> return bitmap
//            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
//            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
//            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
//                matrix.setRotate(180f)
//                matrix.postScale(-1f, 1f)
//            }
//            ExifInterface.ORIENTATION_TRANSPOSE -> {
//                matrix.setRotate(90f)
//                matrix.postScale(-1f, 1f)
//            }
//            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
//            ExifInterface.ORIENTATION_TRANSVERSE -> {
//                matrix.setRotate(-90f)
//                matrix.postScale(-1f, 1f)
//            }
//            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
//            else -> return bitmap
//        }
//        Log.i("MYTAG", "각도 ${exifOrientationToDegrees(orientation)} / ${orientation} / ${ExifInterface.ORIENTATION_ROTATE_90}")
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
//    }
//
//    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
//        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
//            return 90
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
//            return 180
//        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
//            return 270
//        }
//        return 0
//    }

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

    // Uri To Bitmap
    fun loadBitmapFromMediaStoreBy(context: Context, photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) { // Api 버전별 이미지 처리
                val source: ImageDecoder.Source = ImageDecoder.createSource(context.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    // Bitmap To Uri
    fun getBitmapToUriExternalStorage(context: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "IMG_" + System.currentTimeMillis(), null)
        return Uri.parse(path)
    }

    // uri 절대 경로
    fun absolutelyPathExternalStorage(context: Context, path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    fun fileUriToContentUriExternalStorage(context: Context, file: File): Uri? {

        //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri
        val cr = context.contentResolver
        val imagePath = file.absolutePath
        val imageName: String? = null
        val imageDescription: String? = null
        val uriString =
            MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription)
        return Uri.parse(uriString)
    }


    fun contentUriToFileUri(ctx: Context, uri: Uri?): Uri? {
        var cursor: Cursor? = null
        return try {
            cursor = ctx.contentResolver.query(uri!!, null, null, null, null)
            cursor!!.moveToNext()
            Uri.fromFile(File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))))
        } finally {
            cursor?.close()
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


    fun saveUriToInternalStorage(context: Context, uri: Uri): Bitmap {
        val dirPath: String = "${context.filesDir}/PickerImage"
        val filePath: String = "${dirPath}/${System.currentTimeMillis()}.png"

        // 디렉토리 생성
        val makeDir = File(dirPath)
        if(!makeDir.exists()) {
            makeDir.mkdirs()
        }

        val inputStream = context.contentResolver.openInputStream(uri)
        val mBitmap = BitmapFactory.decodeStream(inputStream)
        Log.i("MYTAG", "이미지 파일명: ${System.currentTimeMillis()}")
        val file = File(dirPath, "${System.currentTimeMillis()}.png")
        val fos = FileOutputStream(file)
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        return mBitmap
    }

    // 사진 찍기 후 각도 맞추기
    fun rotateBitmapInternalStorage(bitmap: Bitmap, orientation: Int): Bitmap? {
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
//            val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            val mBitmapRoted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            bitmap.recycle()
            mBitmapRoted
        } catch (e: OutOfMemoryError) {
            e.printStackTrace()
            null
        }
    }

    fun setDirEmpty(dirPath: String) {
        val dir = File(dirPath)
        val childFileList = dir.listFiles()
        if (dir.exists()) {
            for (childFile in childFileList) {
                if (childFile.isDirectory) {
                    setDirEmpty(childFile.absolutePath) //하위 디렉토리
                } else {
                    childFile.delete() //하위 파일
                }
            }
            dir.delete()
        }
    }

    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap) {
        val dirPath: String = "${context.filesDir}/PickerImage"
        val fileName = "${System.currentTimeMillis()}.png"
        val filePath: String = "${dirPath}/${fileName}"

        // 디렉토리 생성
        val makeDir = File(dirPath)
        if(!makeDir.exists()) {
            makeDir.mkdirs()
        }

//        val inputStream = context.contentResolver.openInputStream(uri)
//        val mBitmap = BitmapFactory.decodeStream(inputStream)

//        val file = File(dirPath, "${System.currentTimeMillis()}.png")
//        val fos = FileOutputStream(file)
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//        fos.flush()
//        fos.close()

        try {
            val fos = FileOutputStream(filePath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.i("MYTAG", "이미지 만들기 성공")
    }

    // Bitmap To Uri
    fun bitmapToUriInternalStorage(context: Context, bitmap: Bitmap, fileName: String): Uri {
        val dirPath: String = "${context.filesDir}/PickerImage"
        val filePath: String = "${dirPath}/${fileName}"

        // 디렉토리 생성
        val makeDir = File(dirPath)
        if(!makeDir.exists()) {
            makeDir.mkdirs()
        }

        val file = File(dirPath, fileName)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        val uri = FileProvider.getUriForFile(context, "com.example.multipleimagepicker.fileprovider", file)

        // Android 7.0 이상에서 보안 위험
//        Uri.fromFile(file)

        return uri
    }
}