package com.my.animdemo1

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.my.animdemo1.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var level = 0

    private var animatorSet: AnimatorSet? = null
    private var valueAnimator1: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvHello.setOnClickListener {}

        binding.ivLayerList1.setOnClickListener {}

        binding.tvSelector1.setOnClickListener {
            binding.tvSelector1.isSelected = !binding.tvSelector1.isSelected
            binding.tvSelector1.text = if(binding.tvSelector1.isSelected) {
                "Selector O"
            } else {
                "Selector X"
            }
        }

        binding.ivLevelList1.setOnClickListener {
            level = if(level == 0) 1 else 0
            binding.ivLevelList1.setImageLevel(level)
        }

        val drawable: Drawable = binding.ivTransition1.drawable

        binding.ivTransition1.setOnClickListener {
            (drawable as TransitionDrawable).startTransition(1000)
        }

        valueAnimator1 = ValueAnimator.ofFloat(1.0f, 3.0f, 1.0f).apply {
            interpolator = LinearInterpolator()
            duration = 1000L
            addUpdateListener {
                binding.ivTransition1.scaleX = it.animatedValue as Float
                binding.ivTransition1.scaleY = it.animatedValue as Float
            }
        }

        valueAnimator1?.start()


        binding.activeIconButton1.setIcon(R.drawable.ic_rounded_lock_24, R.drawable.ic_rounded_lock_white_24)
        binding.activeIconButton1.setIconSize(48f)
        binding.activeIconButton1.setOnClickListener {
            if(binding.activeIconButton1.getPercentage().toInt() != 0) {
                return@setOnClickListener
            }
            lifecycleScope.launch {
                for(i in 0 until 101) {
                    delay(50L)
                    binding.activeIconButton1.setPercentage(i)
                }
                binding.activeIconButton1.setPercentage(0)
            }
        }
        binding.activeIconButton2.setIcon(R.drawable.ic_rounded_lock_open_24, R.drawable.ic_rounded_lock_open_white_24)
        binding.activeIconButton2.setIconSize(36f)
        binding.activeIconButton2.setOnClickListener {
            if(binding.activeIconButton2.getPercentage().toInt() != 0) {
                return@setOnClickListener
            }
            binding.activeIconButton2.setAutoPercentage(0)
        }

    }

    override fun onResume() {
        super.onResume()
        AnimUtil.dropFromTop(view = binding.ivTransition1)
        AnimUtil.alpha(view = binding.ivTransition1, 2000L)
    }
}