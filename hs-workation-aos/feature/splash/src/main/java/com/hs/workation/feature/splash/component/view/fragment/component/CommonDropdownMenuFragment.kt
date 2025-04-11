package com.hs.workation.feature.splash.component.view.fragment.component

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.ActiveButton
import com.hs.workation.core.component.CommonDropdown
import com.hs.workation.core.component.CommonToast
import com.hs.workation.core.model.mock.DropdownMenuItem
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentCommonDropdownMenuBinding

class CommonDropdownMenuFragment : BaseDataBindingFragment<FragmentCommonDropdownMenuBinding>(R.layout.fragment_common_dropdown_menu) {

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

        val data: MutableList<DropdownMenuItem> = ArrayList<DropdownMenuItem>()
        for(i in 0 until 20) {
            data.add(DropdownMenuItem("Option ${i}"))
        }

        binding.cvCompose.setContent {
            val currentLocalView = LocalView.current

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ActiveButton(
                        modifier = Modifier
                            .weight(1f),
                        onButtonClick = {

                        },
                        title = "Title",
                        titleColor = Pair(com.hs.workation.core.base.R.color.white, com.hs.workation.core.base.R.color.black),
                        titleSize = 30f,
                        backgroundColor = Pair(com.hs.workation.core.base.R.color.black, com.hs.workation.core.common.R.color.white),
                        borderStrokeColor = Pair(com.hs.workation.core.base.R.color.black, com.hs.workation.core.base.R.color.white)
                    )

                    CommonDropdown(
                        modifier = Modifier
                            .weight(1f),
                        maxWidth = 0,
                        onSelectedListener = { index, dropdownMenuItem ->
                            CommonToast.makeToast(currentLocalView, dropdownMenuItem.menu)
                        },
                        initTitle = "취소 사유를 선택해 주세요",
                        optionMenuList = listOf(
                            DropdownMenuItem("여행 일정이 취소 또는 연기되었기 때문에"),
                            DropdownMenuItem("신뢰도에 대한 우려"),
                            DropdownMenuItem("숙소가 위치한 지역의 안전성 우려"),
                            DropdownMenuItem("기타")
                        ),
                        cornerRadius = 8,
                        titleBackgroundColor = com.hs.workation.core.common.R.color.white,
                        optionMenuBackgroundColor = com.hs.workation.core.common.R.color.white,
                        borderColor = com.hs.workation.core.common.R.color.grey_500,
                        titleTextColor = com.hs.workation.core.common.R.color.grey_600,
                        optionMenuTextColor = com.hs.workation.core.common.R.color.grey_700
                    )
                }
                
                Text(text = "?!?!?!?!?!")

                Row {
                    Spacer(modifier = Modifier.width(10.dp))
                    CommonDropdown(
                        modifier = Modifier
                            .weight(1f),
                        maxWidth = 0,
                        onSelectedListener = { index, dropdownMenu ->
                            CommonToast.makeToast(currentLocalView, dropdownMenu.menu)
                        },
                        initTitle = "취소 사유를 선택해 주세요",
                        optionMenuList = listOf(
                            DropdownMenuItem("여행 일정이 취소 또는 연기되었기 때문에"),
                            DropdownMenuItem("신뢰도에 대한 우려"),
                            DropdownMenuItem("숙소가 위치한 지역의 안전성 우려"),
                            DropdownMenuItem("기타")
                        ),
                        cornerRadius = 8,
                        titleBackgroundColor = com.hs.workation.core.common.R.color.white,
                        optionMenuBackgroundColor = com.hs.workation.core.common.R.color.white,
                        borderColor = com.hs.workation.core.common.R.color.grey_500,
                        titleTextColor = com.hs.workation.core.common.R.color.grey_600,
                        optionMenuTextColor = com.hs.workation.core.common.R.color.grey_700
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Row {
                    Text(text = "Test, Test, Test")

                    CommonDropdown(
                        maxWidth = 200,
                        onSelectedListener = { index, dropdownMenu ->
                            CommonToast.makeToast(currentLocalView, dropdownMenu.menu)
                        },
                        initTitle = "취소 사유를 선택해 주세요",
                        optionMenuList = listOf(
                            DropdownMenuItem("여행 일정이 취소 또는 연기되었기 때문에"),
                            DropdownMenuItem("신뢰도에 대한 우려"),
                            DropdownMenuItem("숙소가 위치한 지역의 안전성 우려"),
                            DropdownMenuItem("기타")
                        ),
                        cornerRadius = 8,
                        titleBackgroundColor = com.hs.workation.core.common.R.color.white,
                        optionMenuBackgroundColor = com.hs.workation.core.common.R.color.white,
                        borderColor = com.hs.workation.core.common.R.color.blue_500,
                        titleTextColor = com.hs.workation.core.common.R.color.cyan_600,
                        optionMenuTextColor = com.hs.workation.core.common.R.color.yellow_700,
                        arrowIconColor = com.hs.workation.core.common.R.color.red_700
                    )

                    Text(text = "Test, Test, Test")
                }

            }
        }

    }

}