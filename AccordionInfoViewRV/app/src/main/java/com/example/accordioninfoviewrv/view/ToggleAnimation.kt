package com.example.accordioninfoviewrv.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

class ToggleAnimation {

    companion object {

        fun toggleArrow(view: View, isExpanded: Boolean): Boolean {
            return if (isExpanded) {
                view.animate().setDuration(200).rotation(90f)
                true
            } else {
                view.animate().setDuration(200).rotation(0f)
                false
            }
        }

        fun expand(view: View, actualHeight: Int) {
//            Log.d("MYTAG", "측정 높이 2: ${view.measuredHeight}")
            val animation = expandAction(view, actualHeight)
            view.startAnimation(animation)
        }

        private fun expandAction(view: View, actualHeight: Int) : Animation {
//            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//            val actualHeight = view.measuredHeight

//            Log.d("MYTAG", "expand actualHeight: ${actualHeight}")

            view.layoutParams.height = 0
            view.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if(interpolatedTime == 1f) {
                        view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
//                        Log.d("MYTAG", "1 expand ${view.layoutParams.height }")
                    } else {
                        view.layoutParams.height = (actualHeight * interpolatedTime).toInt()
//                        Log.d("MYTAG", "2 expand ${view.layoutParams.height }")
                    }

                    view.requestLayout()
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
//            Log.d("MYTAG", "expand duration: ${animation.duration}")

            view.startAnimation(animation)

            return animation
        }

        fun collapse(view: View) {
            val actualHeight = view.measuredHeight

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
//                        Log.d("MYTAG", "2 collapse ${view.layoutParams.height}")
                        view.requestLayout()
                    }
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
//            Log.d("MYTAG", "expand duration: ${animation.duration}")
            view.startAnimation(animation)
        }
    }

}