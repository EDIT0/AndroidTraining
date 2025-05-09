package com.my.cardocrdemo1

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.view.View
import androidx.annotation.OptIn
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.my.cardocrdemo1.util.CameraUtil.executor
import com.my.cardocrdemo1.util.CameraUtil.getCameraProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.nio.ByteBuffer
import java.util.LinkedList
import java.util.regex.Pattern

class CameraViewModel(val app: Application) : AndroidViewModel(app) {

    private lateinit var textRecognizer: TextRecognizer
    private val cardNumberPattern: String = "(\\d{4}[\\s-]?){3}\\d{4}"
    private val cardDatePattern: String = "\\d{2}\\s?/\\s?\\d{2}"
    private val compiledCardNumberPattern: Pattern = Pattern.compile(cardNumberPattern)
    private val compiledCardDatePattern: Pattern = Pattern.compile(cardDatePattern)

    private var _isTakePicture = MutableStateFlow<Boolean>(true)
    val isTakePicture: StateFlow<Boolean> = _isTakePicture.asStateFlow()

    private val dataQueue = LinkedList<CardInfoModel>()
    private val multipleCheckCount = 5

    var cardNumber= ""
    var cardDate = ""

    @SuppressLint("RestrictedApi")
    fun initCamera(view: View, lifecycleOwner: LifecycleOwner) = flow<ImageCapture> {
        val cameraProvider = app.getCameraProvider()

        val buildTakePicture: ImageCapture = ImageCapture.Builder()
//            .setTargetResolution(Size(previewWidth, previewHeight - ((previewHeight / 3) * 2)))
//            .setTargetResolution(Size(500, 100))
//            .setMaxResolution(Size(rootWidth, rootHeight))
//            .setTargetAspectRatio(aspectRatio(rootWidth, rootHeight))
            .setTargetRotation(view.display.rotation)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        try {
            val preview = buildPreview(view as PreviewView)
            val cameraSelector = buildCameraSelector()
            cameraProvider.unbindAll()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                buildTakePicture
            )
        } catch (e: Exception) {
            Log.e("MYTAG", "${e.message}")
            throw e
        }

        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        emit(buildTakePicture)
    }

    @SuppressLint("RestrictedApi")
    private fun buildPreview(previewView: PreviewView): Preview = Preview.Builder()
//        .setTargetResolution(Size(previewWidth, previewHeight - ((previewHeight / 3) * 2)))
//        .setTargetResolution(Size(500, 100))
//        .setMaxResolution(Size(rootWidth, rootHeight))
//        .setTargetAspectRatio(aspectRatio(rootWidth, rootHeight))
        .build()
        .apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

    private fun buildCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    fun takePicture(buildTakePicture: ImageCapture) {
        buildTakePicture.takePicture(
            app.executor,
            object : OnImageCapturedCallback() {
                @OptIn(ExperimentalGetImage::class)
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val bitmap = imageProxyToBitmap(image)?.rotate(image.imageInfo.rotationDegrees.toFloat())
                    // 틀에 맞게 리사이징
                    val resizedBitmap = cropExact(bitmap!!)
                    // 이미지 저장 storage -> self or emulated -> Android -> package name -> files -> Pictures
//                    BitmapUtils.saveBitmapToFile(app.applicationContext, resizedBitmap!!)

                    var number = ""
                    var date = ""

                    viewModelScope.launch {
                        val imageInput = InputImage.fromBitmap(resizedBitmap, image.imageInfo.rotationDegrees)
//                        val imageInput = InputImage.fromMediaImage(
//                            image.image!!,
//                            image.imageInfo.rotationDegrees
//                        )
                        val text = textRecognizer.process(imageInput).await().text

//                        Log.d("MYTAG", "${text}\n")

                        val cardNumberMatcher = compiledCardNumberPattern.matcher(text)
                        while (cardNumberMatcher.find()) {
                            number = cardNumberMatcher.group()
                            Log.d("MYTAG", "인식된 카드번호: ${number}")
                        }

                        val cardDateMatcher = compiledCardDatePattern.matcher(text)
                        while (cardDateMatcher.find()) {
                            date = cardDateMatcher.group()
                            Log.d("MYTAG", "인식된 날짜: ${date}")
                        }

                        dataQueue.push(CardInfoModel(number, date))

                        if(dataQueue.size > multipleCheckCount) {
                            dataQueue.removeLast()
                        }

                        if(dataQueue.size >= multipleCheckCount) {
                            if(areAllElementsEqual(dataQueue)) {
                                _isTakePicture.value = false
                            } else {
                                takePicture(buildTakePicture)
                            }
                        } else {
                            takePicture(buildTakePicture)
                        }
                    }
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("MYTAG", "onError() ${exception.message}")
                }
            })
    }

    private fun areAllElementsEqual(linkedList: LinkedList<CardInfoModel>): Boolean {
        val first = linkedList.peek()
        val n = first?.number
        val d = first?.date

        if(n.isNullOrEmpty() || d.isNullOrEmpty()) {
            return false
        }

        for (element in linkedList) {
            if (n != element.number || d != element.date) {
                return false
            }
        }

        cardNumber = n
        cardDate = d
        return true
    }

    private fun imageProxyToBitmap(image: ImageProxy): Bitmap? {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun Bitmap.rotate(degrees: Float): Bitmap =
        Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)

//    fun resizeBitmapToPreviewSize(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
//        return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
//    }
    fun cropBitmapToCenter(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        // 중앙을 기준으로 크롭할 좌표 계산
        val startX = (bitmapWidth - targetWidth) / 2
        val startY = (bitmapHeight - targetHeight) / 2

        // 크롭된 비트맵 반환
        return Bitmap.createBitmap(bitmap, startX, startY, targetWidth, targetHeight)
    }

    private fun cropExact(bitmap: Bitmap): Bitmap {
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        val (width, height) = cardCaptureImageResizing(bitmapWidth, bitmapHeight)

//        Log.d("MYTAG", "${bitmapWidth}/${bitmapHeight}/${rootWidth}/${rootHeight}/${targetWidth}/${targetHeight}")

        // 자를 크기 계산
        val cropX = 0
        val cropY = (bitmapHeight / 2) - (height / 2)
        val cropWidth = bitmapWidth
        val cropHeight = height

        Log.d("MYTAG", "Crop: x: ${cropX} y: ${cropY} width: ${cropWidth} height: ${cropHeight}")

        // 자르기
        return Bitmap.createBitmap(bitmap, cropX, cropY, cropWidth, cropHeight)
    }

//    private fun aspectRatio(width: Int, height: Int): Int {
//        val previewRatio = max(width, height).toDouble() / min(width, height)
//        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
//            return AspectRatio.RATIO_4_3
//        }
//        return AspectRatio.RATIO_16_9
//    }
//
//    companion object {
//        const val RATIO_4_3_VALUE = 4.0 / 3.0
//        const val RATIO_16_9_VALUE = 16.0 / 9.0
//    }

    private fun cardCaptureImageResizing(width: Int, height: Int): Pair<Int, Int> {
//        val (width, height) = ScreenUtil.getScreenWidthHeight(app)

        Log.d("MYTAG", "스크린 크기: ${width} / ${height}")

        val calWidth: Int = (width / 1.8).toInt()
        val calHeight: Int = (height / 1).toInt()

        if(calWidth >= calHeight) {
            val previewWidth = ((calHeight * 1.8).toInt() / 1).toInt()
            val previewHeight = ((calHeight * 1).toInt() / 1).toInt()
            return Pair<Int, Int>(previewWidth, previewHeight)
        } else {
            val previewWidth = ((calWidth * 1.8).toInt() / 1).toInt()
            val previewHeight = ((calWidth * 1).toInt() / 1).toInt()
            return Pair<Int, Int>(previewWidth, previewHeight)
        }
    }
}