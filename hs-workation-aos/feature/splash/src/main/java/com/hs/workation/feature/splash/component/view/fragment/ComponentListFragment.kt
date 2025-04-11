package com.hs.workation.feature.splash.component.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hs.workation.core.base.view.fragment.BaseDataBindingFragment
import com.hs.workation.feature.splash.R
import com.hs.workation.feature.splash.databinding.FragmentComponentListBinding
import com.hs.workation.feature.splash.component.view.adapter.ComponentAdapter

class ComponentListFragment : BaseDataBindingFragment<FragmentComponentListBinding>(R.layout.fragment_component_list) {

    /**
     * 1. componentList 에 컴포넌트 뷰 이름 추가
     * 2. componentAdapter 초기화 when 문에 1번 이름과 동일하게 추가
     * 3. splash -> view -> fragment -> component 패키지 내에 새로운 Fragment 만든 후 내비게이션(res->navigation->nav_splash) 연결해주고 구현
     */

    private var componentAdapter: ComponentAdapter? = null
    private var componentList = listOf(
        "*NumberIncrease",
        "*CommonSelectSheet",
        "CommonToast",
        "CommonCalendar",
        "InputTextBox",
        "ActiveButton",
        "CommonAgreement",
        "TimelineView",
        "*GroupButtons",
        "CommonDropdownMenu",
        "ExpandableText",
        "PasscodeView",
        "*ImageCarousel",
        "HorizontalItemScroll",
        "AccordionListView",
        "RatingStarView",
        "DateTimePickerView",
        "CommonMap"
    )

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

        binding.tvBack.setOnClickListener {
            findNavController().popBackStack()
        }

        componentAdapter = ComponentAdapter(
            onItemClick = {
                when(it) {
                    "CommonToast" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_commonToastFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "CommonCalendar" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_commonCalendarFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "InputTextBox" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_inputTextBoxFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "ActiveButton" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_activeButtonFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "CommonAgreement" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_commonAgreementFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "TimelineView" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_timelineFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "*GroupButtons" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_radioButtonGroupFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "CommonDropdownMenu" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_commonDropdownMenuFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "ExpandableText" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_expandableTextFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "PasscodeView" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_passcodeFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "*ImageCarousel" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_imageCarouselFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "HorizontalItemScroll" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_horizontalItemScrollFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "AccordionListView" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_accordionListViewFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "RatingStarView" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_ratingStarViewFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "*NumberIncrease" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_numberIncreaseFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "*CommonSelectSheet" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_selectBottomSheetFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "DateTimePickerView" -> {
                        findNavController().navigate(R.id.action_componentFragment_to_dateTimePickerViewFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                    "CommonMap" -> {
//                        val intent = Intent(this.requireContext(), MapActivity::class.java)
//                        this.requireActivity().startActivity(intent)
                        findNavController().navigate(R.id.action_componentFragment_to_commonMapFragment, args = null, navOptions = null, navigatorExtras = null)
                    }
                }
            }
        )

        binding.rvComponent.apply {
            layoutManager = LinearLayoutManager(binding.rvComponent.context, LinearLayoutManager.VERTICAL, false)
            adapter = componentAdapter
            addItemDecoration(DividerItemDecoration(binding.rvComponent.context, LinearLayoutManager.VERTICAL))
        }

        componentAdapter?.submitList(componentList)
    }

}