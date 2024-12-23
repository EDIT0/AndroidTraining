package com.hs.workation.core.component

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hs.workation.core.component.databinding.DialogCommonBinding
import com.hs.workation.core.util.OnSingleClickListener.onSingleClick
import com.hs.workation.core.util.ViewSizeUtil

class CommonDialog(
    context: Context,
    private val percentageWidth: Int = if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        60
    } else {
        80
    }
) : Dialog(context) {

    interface ClickResultCallback {
        fun clickResult(agree: Boolean)
    }

    private var callback: ClickResultCallback? = null
    private var agree : Boolean = false

    private val binding = DialogCommonBinding.inflate(layoutInflater)

    private var windowParams: WindowManager.LayoutParams? = null

    init {
        setContentView(binding.root)

        val params = binding.dialogRootLayout.layoutParams

        params.width = ViewSizeUtil.getDeviceWidth(context) * percentageWidth / 100
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT

        binding.dialogRootLayout.layoutParams = params

        // 최대 높이 설정
        val maxHeight: Int = ViewSizeUtil.getDeviceHeight(context) * 97 / 100

        binding.dialogRootLayout.post {
            val height = binding.dialogRootLayout.height
            val scrollViewParams = binding.scrollDialog.layoutParams

            // 높이가 최대 높이를 초과하면 스크롤 가능하도록 설정
            if (height > maxHeight) {
                scrollViewParams.height = maxHeight
            } else {
                scrollViewParams.height = height
            }

            binding.scrollDialog.layoutParams = scrollViewParams
        }

        windowParams = window?.attributes
    }

    fun setClickResultListener(listener: ClickResultCallback?): CommonDialog {
        callback = listener
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnPositive.onSingleClick {
            agree = true
            dismiss()
        }

        binding.btnNegative.onSingleClick {
            agree = false
            dismiss()
        }
    }

    fun setTransparentBackground(): CommonDialog {
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return this
    }

    fun setDialogCancelable(value: Boolean): CommonDialog {
        setCancelable(value)
        return this
    }

    fun setTitle(title: String): CommonDialog {
        binding.tvTitle.apply {
            visibility = View.VISIBLE
            text = title
        }
        return this
    }

    fun setContents(contents: String): CommonDialog {
        binding.tvContents.apply {
            visibility = View.VISIBLE
            text = contents
        }
        return this
    }

    fun setNegativeText(negativeText: String?): CommonDialog {
        binding.checkLayout.visibility = View.VISIBLE
        binding.btnNegative.visibility = View.VISIBLE
        binding.view.visibility = View.VISIBLE
        negativeText?.let {
            binding.btnNegative.text = negativeText
        }
        return this
    }

    fun setPositiveText(positiveText: String?): CommonDialog {
        binding.checkLayout.visibility = View.VISIBLE
        binding.btnPositive.visibility = View.VISIBLE
        positiveText?.let {
            binding.btnPositive.text = positiveText
        }
        return this
    }

    fun dialogCancel() {
        agree = false
        cancel()
    }

    override fun dismiss() {
        super.dismiss()

        callback?.clickResult(agree)

    }
}