package com.hs.workation.core.component.horizontalscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hs.workation.core.component.databinding.HorizontalItemScrollViewBinding
import com.hs.workation.core.model.mock.HorizontalItemScrollAdapterTypeModel
import com.hs.workation.core.util.ViewSizeUtil

class HorizontalItemScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), HorizontalItemScrollViewContract {

    private var binding: HorizontalItemScrollViewBinding = HorizontalItemScrollViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var horizontalItemScrollViewAdapter: HorizontalItemScrollViewAdapter? = null

    // 클릭된 아이템 콜백
    var onItemClickCallback: ((HorizontalItemScrollAdapterTypeModel) -> Unit)? = null

    // 클릭된 아이템 콜백
    var onSeeMoreClickCallback: (() -> Unit)? = null

    init {
        horizontalItemScrollViewAdapter = HorizontalItemScrollViewAdapter(
            onItemClick = {
                onItemClickCallback?.invoke(it)
            },
            onSeeMoreClick = {
                onSeeMoreClickCallback?.invoke()
            }
        )
        binding.rvHorizontalItemScroll.apply {
            layoutManager = LinearLayoutManager(binding.rvHorizontalItemScroll.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = horizontalItemScrollViewAdapter
        }
    }

    /**
     * Padding 설정
     *
     * @param startPadding dp 단위로 입력
     * @param endPadding dp 단위로 입력
     * @param topPadding dp 단위로 입력
     * @param bottomPadding dp 단위로 입력
     */
    override fun setViewPadding(startPadding: Int, endPadding: Int, topPadding: Int, bottomPadding: Int) {
        binding.rvHorizontalItemScroll.setPadding(
            ViewSizeUtil.dpToPx(binding.rvHorizontalItemScroll.context, startPadding.toFloat()).toInt(),
            ViewSizeUtil.dpToPx(binding.rvHorizontalItemScroll.context, topPadding.toFloat()).toInt(),
            ViewSizeUtil.dpToPx(binding.rvHorizontalItemScroll.context, endPadding.toFloat()).toInt(),
            ViewSizeUtil.dpToPx(binding.rvHorizontalItemScroll.context, bottomPadding.toFloat()).toInt()
        )
    }

    /**
     * 리사이클러뷰 Padding 공백 부분
     *
     * @param isClip 공백 부분 사용 false / 미사용 true
     */
    override fun setHorizontalClipToPadding(isClip: Boolean) {
        binding.rvHorizontalItemScroll.clipToPadding = isClip
    }

    /**
     * 이미지 사이즈 설정
     *
     * @param width 이미지 너비 (dp)
     * @param height 이미지 높이 (dp)
     */
    override fun setImageSize(width: Int, height: Int) {
        horizontalItemScrollViewAdapter?.setImageSize(width = width, height = height)
    }

    /**
     * 아이템과 아이템 사이 간격
     *
     * @param padding dp
     */
    override fun setItemToItemMargin(margin: Int) {
        horizontalItemScrollViewAdapter?.setItemToItemMargin(margin = margin)
    }

    /**
     * 아이템 리스트 설정
     *
     * @param list
     */
    override fun setItem(list: List<HorizontalItemScrollAdapterTypeModel>) {
        horizontalItemScrollViewAdapter?.submitList(list)
    }
}