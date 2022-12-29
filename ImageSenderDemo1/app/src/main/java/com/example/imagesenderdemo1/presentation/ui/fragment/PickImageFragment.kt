package com.example.imagesenderdemo1.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.util.Utility
import com.example.imagesenderdemo1.databinding.FragmentPickImageBinding
import com.example.imagesenderdemo1.presentation.ui.activity.MainActivity

class PickImageFragment : Fragment() {

    val TAG = PickImageFragment::class.java.simpleName

    private lateinit var binding: FragmentPickImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pick_image, container, false)
        binding.pickImageFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun getImageFromGallery() {
        Utility.openGallery(activity as MainActivity)
    }

    fun getCamera() {
        Utility.openCamera(activity as MainActivity)
    }

    fun getImagesFromGallery() {
        Utility.openMultiGallery(activity as MainActivity)
    }

}