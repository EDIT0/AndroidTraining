package com.example.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import com.example.bottomnavigation.databinding.FragmentThreeBinding
import com.example.bottomnavigation.databinding.FragmentTwoBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils

class ThreeFragment : Fragment() {

    private val TAG = ThreeFragment::class.java.simpleName

    private var _fragmentThreeBinding: FragmentThreeBinding? = null
    private val fragmentThreeBinding get() = _fragmentThreeBinding!!

    private lateinit var badgeDrawable: BadgeDrawable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentThreeBinding = FragmentThreeBinding.inflate(inflater, container, false)
        val view = fragmentThreeBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated()")

        fragmentThreeBinding.tvThree.setOnClickListener {
            (requireActivity() as MainActivity).showToast("${TAG}")
        }

        fragmentThreeBinding.tvThree.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                badgeDrawable = BadgeDrawable.create(fragmentThreeBinding.root.context)
                badgeDrawable.apply {
                    number = 20 // 뱃지 드로어블에 표시될 숫자를 설정합니다
                    isVisible = true // 화면에 표시될지를 설정합니다.
                    // 각각 가로, 세로로 이 뱃지가 부착될 anchor의 가운데를 기준으로 어떻게 배치될지를 결정합니다. 뱃지는 기본적으로 우측 상단에 배치되므로, 각각의 값이 커질수록 좌측 하단으로 이동합니다.
                    horizontalOffset = 100
                    verticalOffset = 30
                    backgroundColor = resources.getColor(R.color.teal_200)
                    badgeTextColor = resources.getColor(R.color.purple_200)
                    badgeGravity = BadgeDrawable.BOTTOM_START
                }

                // 이 뱃지를 원하는 곳에 배치합니다. 세번째 파라미터는 customBadgeParent로, 원하는 레이아웃이 있을 때 사용합니다.
                BadgeUtils.attachBadgeDrawable(badgeDrawable, fragmentThreeBinding.tvThree, null)

                fragmentThreeBinding.tvThree.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()

        Log.i(TAG, "onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentThreeBinding = null
        Log.i(TAG, "onDestroyView()")
    }
}