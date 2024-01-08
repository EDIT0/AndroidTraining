package com.example.stepview

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.stepview.databinding.FragmentStepViewExampleBinding
import com.example.stepview.view.StepViewModel
import com.shuhart.stepview.StepView

class StepViewExampleFragment : Fragment() {

    private lateinit var binding: FragmentStepViewExampleBinding

    private val arr = ArrayList<StepViewModel>()
    private val openArr = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStepViewExampleBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        customStepView()
        libraryStepView()
        btnClick()
    }

    private fun customStepView() {
        arr.add(StepViewModel(0, "0", ""))
        arr.add(StepViewModel(1, "1", ""))
        arr.add(StepViewModel(2, "2", ""))
        arr.add(StepViewModel(3, "3", ""))
        arr.add(StepViewModel(4, "4", ""))
        arr.add(StepViewModel(5, "5", ""))

        binding.stepView.apply {
            initStepView()
            setList(arr)
            setSelectedImg(R.drawable.close_default_red_24)
            setCompletedImg(R.drawable.ic_check_box_primary_24_active)
            setBeforeImg(R.drawable.power_train_default_black_24)
            setSelectedContentsTextColor(R.color.color_ffd600)
            setCompletedContentsTextColor(R.color.color_99_ee4b55)
            setBeforeContentsTextColor(R.color.color_80_000000)
            setCompletedLineColor(R.color.safety_driving_info_good)
            setBeforeLineColor(R.color.color_ffee8d)
            openTitle()
            isStepClickable(true)
            show()
        }
    }

    private fun libraryStepView() {
        openArr.apply {
            add("Step 1")
            add("Step 2")
            add("Step 3")
            add("Step 4")
            add("Step 5")
            add("Step 6")
        }

        binding.openStepView.apply {
            // done: 이미 지난 원 / selected: 현재 선택된 원 / next: 아직 선택되기 전인 원
            state
                .animationType(StepView.ANIMATION_LINE) // StepView 유형
                .selectedTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.color_e6_538fc3
                    )
                ) // 현재 선택된 하단 텍스트
                .selectedCircleColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.safety_driving_info_good
                    )
                ) // 현재 선택된 원의 색깔
                .selectedCircleRadius(dpToPx(binding.root.context, 15f)) // 현재 선택된 원의 반지름
                .selectedStepNumberColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.color_e6_538fc3
                    )
                ) // 현재 선택된 원의 숫자 색깔
                .steps(openArr)
                .doneCircleColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.dte_gauge_red
                    )
                ) // 이미 단계가 지난 원의 색깔
                .doneCircleRadius(dpToPx(binding.root.context, 15f)) // 이미 단계가 지난 원의 반지름
                .doneStepLineColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                ) // 선택된 원 전 까지의 줄 색깔
                .doneTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                ) // 이미 단계가 지난 원의 텍스트 색깔
                .nextStepLineColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.color_e6_538fc3
                    )
                ) // 선택된 원 이후의 줄 색깔
                .nextTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.color_e6_538fc3
                    )
                ) // 선택된 원 이후의 원의 하단 텍스트 색깔
                .nextStepCircleColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.safety_driving_info_good
                    )
                ) // 선택된 원 이후의 원 색깔
                .nextStepCircleEnabled(true) // 선택된 원 이후의 원들이 보여질지 여부
                .animationDuration(500)
                .stepLineWidth(dpToPx(binding.root.context, 1f)) // 선택되기 전 줄 너비
                .textSize(dpToPx(binding.root.context, 16f)) // 원의 하단 텍스트 사이즈
                .stepNumberTextSize(dpToPx(binding.root.context, 16f)) // 원 내부의 텍스트 사이즈
//                .typeface(ResourcesCompat.getFont(context, R.font.roboto_italic))
                .commit()
        }
    }

    private fun btnClick() {
        binding.btnStepUp.setOnClickListener {
            if (binding.stepView.getCurrentStep() > binding.stepView.getStepListSize()) {
                return@setOnClickListener
            }
            binding.stepView.setCurrentStep(binding.stepView.getCurrentStep() + 1)
        }

        binding.btnStepDown.setOnClickListener {
            if (binding.stepView.getCurrentStep() == 1) {
                return@setOnClickListener
            }
            binding.stepView.setCurrentStep(binding.stepView.getCurrentStep() - 1)
        }

        binding.btnStepCountUp.setOnClickListener {
            arr.add(arr.lastIndex + 1, StepViewModel(arr.lastIndex + 1, "${arr.lastIndex + 1}", ""))
            binding.stepView.apply {
                initStepView()
                setList(arr)
                setSelectedImg(R.drawable.close_default_red_24)
                setCompletedImg(R.drawable.ic_check_box_primary_24_active)
                setBeforeImg(R.drawable.power_train_default_black_24)
                setSelectedContentsTextColor(R.color.color_ffd600)
                setCompletedContentsTextColor(R.color.color_99_ee4b55)
                setBeforeContentsTextColor(R.color.color_80_000000)
                setCompletedLineColor(R.color.safety_driving_info_good)
                setBeforeLineColor(R.color.color_ffee8d)
                openTitle()
                isStepClickable(true)
                show()
            }
        }

        binding.btnStepCountDown.setOnClickListener {
            if (arr.lastIndex <= 0) return@setOnClickListener

            arr.removeAt(arr.lastIndex)
            binding.stepView.apply {
                initStepView()
                setList(arr)
//                setSelectedImg(R.drawable.close_default_red_24)
//                setCompletedImg(R.drawable.ic_check_box_primary_24_active)
//                setBeforeImg(R.drawable.power_train_default_black_24)
//                setSelectedContentsTextColor(R.color.color_ffd600)
//                setCompletedContentsTextColor(R.color.color_99_ee4b55)
//                setBeforeContentsTextColor(R.color.color_80_000000)
//                setCompletedLineColor(R.color.safety_driving_info_good)
//                setBeforeLineColor(R.color.color_ffee8d)
                openTitle()
                isStepClickable(false)
                show()
            }
        }

        binding.btnCurrentStep.setOnClickListener {
            binding.tvCurrentStep.text =
                "현재 단계: Custom(1~) - ${binding.stepView.getCurrentStep()} / Library(0~) - ${binding.openStepView.currentStep}"
        }

        binding.btnOpenStepUp.setOnClickListener {
            if ((openArr.size - 1) <= binding.openStepView.currentStep) {
                binding.openStepView.done(true)
                return@setOnClickListener
            }
            binding.openStepView.go(binding.openStepView.currentStep + 1, true)
        }

        binding.btnOpenStepDown.setOnClickListener {
            if (binding.openStepView.currentStep == 0) {
                return@setOnClickListener
            }
            binding.openStepView.go(binding.openStepView.currentStep - 1, true)
        }
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}