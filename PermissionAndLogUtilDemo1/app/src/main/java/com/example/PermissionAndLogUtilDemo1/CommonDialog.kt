package com.example.PermissionAndLogUtilDemo1

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.example.PermissionAndLogUtilDemo1.databinding.DialogCommonBinding

class CommonDialog(
    context: Context,
) : Dialog(context) {

    interface ClickResultCallback {
        fun clickResult(agree: Boolean?)
    }

    private var callback: ClickResultCallback? = null
    private var agree : Boolean? = null

    private val binding = DialogCommonBinding.inflate(layoutInflater)

    private var windowParams: WindowManager.LayoutParams? = null

    init {
        setContentView(binding.root)

        val params = binding.dialogRootLayout.layoutParams
        val orientation: Int = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            params.width = getDeviceWidth(context) / 100 * 60
        } else {
            // In portrait
            params.width = getDeviceWidth(context) / 100 * 85
        }
//        params.width = (320 * context.resources.displayMetrics.density).toInt()
        binding.dialogRootLayout.layoutParams = params

        windowParams = window?.attributes
    }

    fun setClickResultListener(listener: ClickResultCallback?) {
        callback = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnPositive.setOnClickListener {
            agree = true
            dismiss()
        }

        binding.btnNegative.setOnClickListener {
            agree = false
            dismiss()
        }
    }

    fun setDialogCancelable(value: Boolean) {
        setCancelable(value)
    }

    fun setTitle(title: String) {
        binding.tvTitle.apply {
            visibility = View.VISIBLE
            text = title
        }
    }

    fun setContents(contents: String) {
        binding.tvContents.apply {
            visibility = View.VISIBLE
            text = contents
        }
    }
    fun setNegativeText(negativeText: String?) {
        binding.checkLayout.visibility = View.VISIBLE
        binding.btnNegative.visibility = View.VISIBLE
        binding.view.visibility = View.VISIBLE
        negativeText?.let {
            binding.btnNegative.text = negativeText
        }
    }

    fun setPositiveText(positiveText: String?) {
        binding.checkLayout.visibility = View.VISIBLE
        binding.btnPositive.visibility = View.VISIBLE
        positiveText?.let {
            binding.btnPositive.text = positiveText
        }
    }

    fun dialogCancel() {
        agree = null
        cancel()
    }

    override fun dismiss() {
        super.dismiss()

        callback?.clickResult(agree)

    }

    fun getDeviceWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = wm.currentWindowMetrics
//            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
//            windowMetrics.bounds.width() - insets.left - insets.right
            windowMetrics.bounds.width()
        } else {
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }
}