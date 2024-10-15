package com.my.cardocrdemo1

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import android.util.Size
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
import java.io.ByteArrayOutputStream
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
    fun initCamera(view: View, lifecycleOwner: LifecycleOwner, previewWidth: Int, previewHeight: Int) = flow<ImageCapture> {
        val cameraProvider = app.getCameraProvider()

        val buildTakePicture: ImageCapture = ImageCapture.Builder()
//            .setTargetResolution(Size(previewWidth, previewHeight - ((previewHeight / 3) * 2)))
//            .setTargetResolution(Size(previewWidth, previewHeight))
            .setMaxResolution(Size(1000, 1000))
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(view.display.rotation)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        try {
            val preview = buildPreview(view as PreviewView, previewWidth, previewHeight)
            val cameraSelector = buildCameraSelector()

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
    private fun buildPreview(previewView: PreviewView, previewWidth: Int, previewHeight: Int): Preview = Preview.Builder()
//        .setTargetResolution(Size(previewWidth, previewHeight - ((previewHeight / 3) * 2)))
//        .setTargetResolution(Size(previewWidth, previewHeight))
        .setMaxResolution(Size(1000, 1000))
        .setTargetAspectRatio(AspectRatio.RATIO_16_9)
        .build()
        .apply {
            setSurfaceProvider(previewView.surfaceProvider)
        }

    private fun buildCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()

    fun takePicture(buildTakePicture: ImageCapture, previewWidth: Int, previewHeight: Int) {
        buildTakePicture.takePicture(
            app.executor,
            object : OnImageCapturedCallback() {
                @OptIn(ExperimentalGetImage::class)
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

//                    val bitmap = imageProxyToBitmap(image)?.rotate(image.imageInfo.rotationDegrees.toFloat())
//                    val resizedBitmap = cropBitmapToCenter(bitmap!!, previewWidth, previewHeight)
//                    BitmapUtils.saveBitmapToFile(app.applicationContext, bitmap!!)

                    var number = ""
                    var date = ""

                    viewModelScope.launch {
                        val imageInput = InputImage.fromMediaImage(
                            image.image!!,
                            image.imageInfo.rotationDegrees
                        )
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
                                takePicture(buildTakePicture, previewWidth, previewHeight)
                            }
                        } else {
                            takePicture(buildTakePicture, previewWidth, previewHeight)
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
}