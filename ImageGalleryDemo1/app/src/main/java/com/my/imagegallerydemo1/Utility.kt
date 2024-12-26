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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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


    /**
     * MultipartBody 1장
     *
     * @param file 파일 File(path)
     * @param fileName 파일 이름
     * @return
     */
    fun makeMultipartBody(file: File, fileName: String): MultipartBody.Part {
        /**
         * image/jpg: MIME 타입 지정
         * .toMediaTypeOrNull(): MediaType 객체로 변환
         */
        val requestBody : RequestBody = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        /**
         * uploaded_file: 서버에서 이 이름으로 파일을 찾음 (서버와 약속된 키 값)
         * fileName: 서버에 전달될 실제 파일 이름
         * requestBody: 실제 파일 데이터
         */
        val body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
        return body
    }

    /**
     *     @Multipart
     *     @POST("SendImageToServer.php")
     *     suspend fun sendImageToServer(
     *         @Part("fileName") fileName: String,
     *         @Part imageFile : MultipartBody.Part
     *     ): Response<IsSuccessSendImageModel>
     *
     *     @Multipart
     *     @POST("SendImagesToServer.php")
     *     suspend fun sendImagesToServer(
     *         @Part("fileNameArray") fileNameArray: ArrayList<String>,
     *         @Part imageFileArray : ArrayList<MultipartBody.Part>
     *     ): Response<IsSuccessSendImageModel>
     */

    /**
     * MultipartBody 1장 이상
     *
     * @param fileArray
     * @param fileNameArray
     * @return
     */
    fun makeMultipartArrayBody(fileArray: ArrayList<File>, fileNameArray: ArrayList<String>) : ArrayList<MultipartBody.Part> {
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
}