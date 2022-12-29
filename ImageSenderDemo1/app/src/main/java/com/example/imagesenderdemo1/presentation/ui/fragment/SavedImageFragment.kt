package com.example.imagesenderdemo1.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesenderdemo1.R
import com.example.imagesenderdemo1.data.model.SavedImageModel
import com.example.imagesenderdemo1.databinding.FragmentSavedImageBinding
import com.example.imagesenderdemo1.presentation.adapter.SavedImageAdapter
import com.example.imagesenderdemo1.presentation.ui.activity.ImageZoomInOutActivity
import com.example.imagesenderdemo1.presentation.ui.activity.MainActivity
import kotlinx.coroutines.launch


class SavedImageFragment : Fragment() {

    val TAG = SavedImageFragment::class.java.simpleName

    private lateinit var binding: FragmentSavedImageBinding
    lateinit var savedImageAdapter: SavedImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_image, container, false)
        binding.savedImageFragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedImageAdapter = SavedImageAdapter()

        val gridLayoutManager = GridLayoutManager(activity, 3)
        binding.rvSavedImage.layoutManager = gridLayoutManager

        binding.rvSavedImage.adapter = savedImageAdapter
        binding.rvSavedImage.addOnScrollListener(onScrollListener)


//        val a = listOf<SavedImageModel>(
//            SavedImageModel(0, "good"),
//            SavedImageModel(1, "good"),
//            SavedImageModel(2, "good"),
//            SavedImageModel(3, "good"),
//            SavedImageModel(4, "good"),
//            SavedImageModel(5, "good"),
//            SavedImageModel(6, "good")
//        )
//        savedImageAdapter.replaceItems(a)
//        savedImageAdapter.notifyDataSetChanged()

        (activity as MainActivity).mainViewModel.selectTotalSavedImage("Test")

        observer()
        buttonClickListener()
    }

    private fun buttonClickListener() {
        savedImageAdapter.setOnItemClickListener { savedImageItemBinding, position, savedImageModel ->
            val intent = Intent(activity, ImageZoomInOutActivity::class.java)
            intent.putExtra("image_idx", savedImageModel.idx)
            intent.putExtra("image_address", savedImageModel.imageAddress)
            startActivity(intent)
        }
    }

    private fun observer() {
        (activity as MainActivity).mainViewModel.selectTotalSavedImage.observe(viewLifecycleOwner) {
            it ?: return@observe
            savedImageAdapter.submitList(it.selectTotalSavedImageModel){
                savedImageAdapter.notifyDataSetChanged()
            }
        }
    }


    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1


            (activity as MainActivity).mainViewModel.apply {
                if (!isLoading.value!!) {
                    // 스크롤이 끝에 도달했는지 확인
                    if (lastVisibleItemPosition == itemTotalCount) {
                        if (isLoading.value!!) {

                        } else {
                            selectTotalSavedImage("Test")
                        }
                    }
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

}