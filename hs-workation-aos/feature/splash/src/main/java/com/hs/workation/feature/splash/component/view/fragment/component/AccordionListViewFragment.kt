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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.core.component.AccordionListView
import com.hs.workation.core.model.mock.AccordionListItem
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentAccordionListViewBinding

class AccordionListViewFragment : BaseDataBindingFragment<FragmentAccordionListViewBinding>(R.layout.fragment_accordion_list_view) {

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

        binding.cvCompose.setContent {
            Column {
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    AccordionListView(
                        modifier = Modifier
                            .weight(1f),
                        title = "총 3건",
                        infoList = listOf(
                            AccordionListItem(title = "침대시트 오염"),
                            AccordionListItem(title = "카드키 분실"),
                            AccordionListItem(title = "의자 파손")
                        ),
                        isShowTitleAndListDivider = true,
                        isShowListDivider = false,
                        titleTextSize = 30,
                        listTitleTextSize = 15,
                        titleTextColor = com.hs.workation.core.common.R.color.grey_600,
                        listTitleTextColor = com.hs.workation.core.common.R.color.grey_600,
                        iconColor = com.hs.workation.core.common.R.color.grey_600
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                }

                AccordionListView(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = "Main Title",
                    infoList = listOf(
                        AccordionListItem(title = "1번"),
                        AccordionListItem(title = "2번"),
                        AccordionListItem(title = "3번"),
                        AccordionListItem(title = "4번"),
                        AccordionListItem(title = "5번"),
                        AccordionListItem(title = "6번"),
                        AccordionListItem(title = "1번"),
                        AccordionListItem(title = "2번"),
                        AccordionListItem(title = "3번"),
                        AccordionListItem(title = "4번"),
                        AccordionListItem(title = "5번"),
                        AccordionListItem(title = "6번"),
                        AccordionListItem(title = "1번"),
                        AccordionListItem(title = "2번"),
                        AccordionListItem(title = "3번"),
                        AccordionListItem(title = "4번"),
                        AccordionListItem(title = "5번"),
                        AccordionListItem(title = "6번"),
                        AccordionListItem(title = "1번"),
                        AccordionListItem(title = "2번"),
                        AccordionListItem(title = "3번"),
                        AccordionListItem(title = "4번"),
                        AccordionListItem(title = "5번"),
                        AccordionListItem(title = "6번"),
                        AccordionListItem(title = "1번"),
                        AccordionListItem(title = "2번"),
                        AccordionListItem(title = "3번"),
                        AccordionListItem(title = "4번"),
                        AccordionListItem(title = "5번"),
                        AccordionListItem(title = "6번")
                    ),
                    isShowTitleAndListDivider = false,
                    isShowListDivider = true,
                    borderColor = com.hs.workation.core.common.R.color.purple_200,
                    backgroundColor = com.hs.workation.core.base.R.color.teal_200,
                    titleTextSize = 20,
                    listTitleTextSize = 18,
                    titleTextColor = com.hs.workation.core.common.R.color.teal_600,
                    listTitleTextColor = com.hs.workation.core.common.R.color.orange_600,
                    iconColor = com.hs.workation.core.common.R.color.blue_A700
                )
            }


        }

    }
}