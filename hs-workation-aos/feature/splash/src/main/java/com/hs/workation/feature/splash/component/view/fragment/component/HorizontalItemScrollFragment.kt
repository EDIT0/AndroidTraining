package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.model.mock.HorizontalItemScrollAdapterViewHolderType
import com.hs.workation.core.model.mock.HorizontalScrollItem
import com.hs.workation.core.model.mock.SeeMoreItem
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentHorizontalItemScrollBinding

class HorizontalItemScrollFragment : BaseDataBindingFragment<FragmentHorizontalItemScrollBinding>(R.layout.fragment_horizontal_item_scroll) {

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

        binding.vHorizontalItemScrollView.apply {
            setViewPadding(startPadding = 20, endPadding = 20, topPadding = 10, bottomPadding = 10)
            setHorizontalClipToPadding(isClip = false)
            setImageSize(width = 300, 500)
            setItemToItemMargin(margin = 15)
            setItem(
                list = listOf(
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 1", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 2", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 6", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = null, title = "Title 7", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = null, title = "Title 8", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 9", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 10", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    HorizontalScrollItem(imageUrl = "https://picsum.photos/200/300", title = "Title 11", type = HorizontalItemScrollAdapterViewHolderType.NORMAL),
                    SeeMoreItem(type = HorizontalItemScrollAdapterViewHolderType.SEE_MORE)
                )
            )
            onItemClickCallback = { item ->
                showToast("클릭된 아이템: ${item}")
            }
            onSeeMoreClickCallback = {
                showToast("더보기 클릭")
            }
        }

    }
}