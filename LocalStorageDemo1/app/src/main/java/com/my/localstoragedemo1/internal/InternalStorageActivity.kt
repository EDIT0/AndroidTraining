package com.my.localstoragedemo1.internal

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.my.localstoragedemo1.FileListAdapter
import com.my.localstoragedemo1.MainActivity.Companion.BASE_IMAGE_PATH
import com.my.localstoragedemo1.MainActivity.Companion.BASE_TEXT_PATH
import com.my.localstoragedemo1.R
import com.my.localstoragedemo1.internal.InternalStorageUtil.saveImage
import com.my.localstoragedemo1.databinding.ActivityInternalStorageBinding
import kotlin.random.Random

class InternalStorageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInternalStorageBinding

    private var fileList = mutableListOf<String>()
    private lateinit var fileListAdapter: FileListAdapter
    private var selectedPath = ""
    private var selectedFileName = "선택된 파일명: -"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInternalStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fileListAdapter = FileListAdapter(
            onItemClick = {
                selectedFileName = it
                binding.tvSelectedFileName.text = "선택된 파일명: " + selectedFileName
            }
        )

        binding.rvFileList.apply {
            adapter = fileListAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        binding.btnGetImageList.setOnClickListener {
            fileList = InternalStorageUtil.getFileList(
                context = applicationContext,
                path = BASE_IMAGE_PATH
            ).toMutableList()
            selectedPath = "/image"
            fileListAdapter.submitList(fileList)
        }

        binding.btnGetTextList.setOnClickListener {
            fileList = InternalStorageUtil.getFileList(
                context = applicationContext,
                path = BASE_TEXT_PATH
            ).toMutableList()
            selectedPath = "/text"
            fileListAdapter.submitList(fileList)
        }

        binding.btnLoadImage.setOnClickListener {
            openGallery()
        }

        binding.btnLoadImages.setOnClickListener {
            openGalleryForMultipleImages()
        }

        binding.btnSaveText.setOnClickListener {
            val number = Random.nextInt(10)
            InternalStorageUtil.saveText(
                context = applicationContext,
                path = BASE_TEXT_PATH,
                fileName = "text_file_${number}",
                text = "${number} 안녕하세요.\n\n반갑습니다. 그럼 이만\n감사합니다."
            )
        }

        binding.btnLoadText.setOnClickListener {
            val loadText = InternalStorageUtil.loadText(
                context = applicationContext,
                path = BASE_TEXT_PATH,
                fileName = selectedFileName
            )
            Log.d("MYTAG", "Load text ${loadText}")
        }

        binding.btnDeleteFile.setOnClickListener {
            val isSuccess = InternalStorageUtil.deleteFile(
                context = applicationContext,
                path = selectedPath,
                fileName = selectedFileName
            )

            if(isSuccess) {
                Log.d("MYTAG", "삭제 성공 ${selectedPath}/${selectedFileName}")
            } else {
                Log.d("MYTAG", "삭제 실패 ${selectedPath}/${selectedFileName}")
            }

            if(selectedPath == BASE_IMAGE_PATH) {
                binding.btnGetImageList.performClick()
            } else if(selectedPath == BASE_TEXT_PATH) {
                binding.btnGetTextList.performClick()
            }
        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            // 이미지 URI를 사용해 처리
            Log.d("MYTAG", "(한장) 원본 URI: $it")
            Log.d("MYTAG", "(한장) 원본 절대경로(복사해서 사용할 것이기 때문에 필요없음): ${InternalStorageUtil.getAbsolutePath(applicationContext, "images", it)}")
            val bitmap = InternalStorageUtil.getBitmapFromUri(this@InternalStorageActivity, it) ?:return@let
            val newUri = saveImage(applicationContext, BASE_IMAGE_PATH, InternalStorageUtil.getFileNameFromUri(applicationContext, it) ?:"", bitmap)
            Log.d("MYTAG", "(한장) 새로운 URI ${newUri}")
            newUri?.let {
                Log.d("MYTAG", "(한장) 새로운 절대경로: ${InternalStorageUtil.getAbsolutePath(applicationContext, BASE_IMAGE_PATH, it)}")
            }
        }
    }

    /**
     * 갤러리 열기 (한장)
     */
    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

    private val pickImagesLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        uris?.let { uriList ->
            uriList.forEach { uri ->
                // 여러 이미지 URI를 사용해 처리
                Log.d("MYTAG", "(여러장) 원본 URI: $uri")
                Log.d("MYTAG", "(여러장) 원본 절대경로(복사해서 사용할 것이기 때문에 필요없음): ${InternalStorageUtil.getAbsolutePath(applicationContext, "images", uri)}")
                val bitmap = InternalStorageUtil.getBitmapFromUri(this@InternalStorageActivity, uri)
                    ?: return@forEach
                val newUri = saveImage(applicationContext, BASE_IMAGE_PATH, InternalStorageUtil.getFileNameFromUri(applicationContext, uri) ?: "", bitmap)
                Log.d("MYTAG", "(여러장) 새로운 URI ${newUri}")
                newUri?.let {
                    Log.d("MYTAG", "(여러장) 새로운 절대경로: ${InternalStorageUtil.getAbsolutePath(applicationContext, BASE_IMAGE_PATH, it)}")
                }
            }
        }
    }

    /**
     * 갤러리 열기 (여러 장)
     */
    private fun openGalleryForMultipleImages() {
        pickImagesLauncher.launch("image/*")
    }
}