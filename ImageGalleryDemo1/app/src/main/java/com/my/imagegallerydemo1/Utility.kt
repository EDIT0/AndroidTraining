package com.my.imagegallerydemo1

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object Utility {

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
        // 원본 코드
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = MediaStore.Images.Media.insertImage(activity.contentResolver, inImage, "IMG_" + System.currentTimeMillis(), null)
//        return Uri.parse(path)

        // 경로 수정 코드
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var uri: Uri? = null

        try {
            // Android 10 (Q) 이상
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues()
                contentValues.apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/imagegallery")
                }

                uri = activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                uri?.let { uri ->
                    activity.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        inImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }
            }
            // Android 9 (Pie) 이하
            else {
                val imagesDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "imagegallery")
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs()
                }

                val image = File(imagesDir, filename)
                FileOutputStream(image).use { outputStream ->
                    inImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }

                uri = Uri.fromFile(image)

                // MediaStore에 등록하여 갤러리에서 보이게 하기
                val values = ContentValues()
                values.apply {
                    put(MediaStore.Images.Media.DATA, image.absolutePath)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                }

                activity.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return uri ?: Uri.EMPTY
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

    fun getOrientation(activity: Activity, imageUri: Uri): Int? {
        var exif: ExifInterface? = null
        try {
            val inputStream: InputStream = activity.contentResolver.openInputStream(imageUri)!!
            exif = ExifInterface(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return exif.getAttributeInt(
            ExifInterface.ORIENTATION_ROTATE_90.toString(),
            ExifInterface.ORIENTATION_UNDEFINED
        )
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
            val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
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
                context.contentResolver.openInputStream(uri),
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
                context.contentResolver.openInputStream(uri),
                null,
                options
            ) //3번
            resizeBitmap = bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return resizeBitmap
    }
}