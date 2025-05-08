package com.hs.workation.feature.splash.test2.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.util.OnSingleClickListener.onSingleClick
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentTest2Binding
import com.hs.workation.feature.splash.test2.event.Test2ViewModelEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Test2Fragment : BaseDataBindingFragment<FragmentTest2Binding>(R.layout.fragment_test_2){

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            // 이미지 URI를 사용해 처리
            Log.d("MYTAG", "(한장) 원본 URI: $it")
            requestViewModelEvent(Test2ViewModelEvent.SaveImage(listOf(it)))
        }
    }

    private val pickImagesLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri>? ->
        uris?.let { uriList ->
            requestViewModelEvent(Test2ViewModelEvent.SaveImage(uriList))
        }
    }

    private val test2VM: Test2ViewModel by viewModels()

    private var backPressCallback: OnBackPressedCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        backPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressCallback!!)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarPadding(view = binding.root, isPadding = true, isStatusIconBlack = true)

        viewClickListener()

    }

    private fun viewClickListener() {
        binding.btnCallApiTest.onSingleClick {
            requestViewModelEvent(Test2ViewModelEvent.CallApiTest())
        }

        binding.btnOpenGallery.onSingleClick(500L) {
            pickImagesLauncher.launch("image/*")
        }
    }

    private fun requestViewModelEvent(test2ViewModelEvent: Test2ViewModelEvent) {
        when(test2ViewModelEvent) {
            is Test2ViewModelEvent.CallApiTest -> {
                test2VM.handleViewModelEvent(test2ViewModelEvent)
            }
            is Test2ViewModelEvent.SaveImage -> {
                test2VM.handleViewModelEvent(test2ViewModelEvent)
            }
        }
    }

    override fun onDetach() {
        backPressCallback?.remove()
        super.onDetach()
    }
}