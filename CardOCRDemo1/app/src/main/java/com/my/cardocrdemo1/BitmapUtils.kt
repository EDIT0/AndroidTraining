package com.my.cardocrdemo1

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object BitmapUtils {

    // 비트맵을 파일로 저장하는 함수
    fun saveBitmapToFile(context: Context, bitmap: Bitmap, format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG, quality: Int = 100): File? {
        // 이미지 파일 이름 생성
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "IMG_$timeStamp.${getFileExtension(format)}"
        
        // 파일 저장 경로 설정 (기본적으로 외부 저장소 Pictures 폴더에 저장)
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, fileName)

        return try {
            // 파일 저장
            val fos = FileOutputStream(imageFile)
            bitmap.compress(format, quality, fos)
            fos.flush()
            fos.close()
            imageFile // 파일이 성공적으로 저장되면 파일 경로 반환
        } catch (e: IOException) {
            e.printStackTrace()
            null // 저장 실패 시 null 반환
        }
    }

    // 확장자 가져오는 함수
    private fun getFileExtension(format: Bitmap.CompressFormat): String {
        return when (format) {
            Bitmap.CompressFormat.JPEG -> "jpg"
            Bitmap.CompressFormat.PNG -> "png"
            Bitmap.CompressFormat.WEBP -> "webp"
            else -> "png"
        }
    }
}