package com.example.multipleimagepicker.ui.base

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class DataBindingBaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) : AppCompatActivity() {
    protected lateinit var binding: T
    lateinit var mActivity: Activity
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        mActivity = this
    }

    open fun showToast(message: String?) {
        toast?.cancel()
        toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}