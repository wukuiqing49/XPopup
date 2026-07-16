package com.lxj.xpopupdemo.fragment

import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.animator.PopupAnimator
import com.lxj.xpopupdemo.R

/**
 * Description:
 * Create by dance, at 2018/12/9
 */
class CustomAnimatorDemo : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_custom_animator_demo

    override fun init(view: View) {
        view.findViewById<View?>(R.id.btn_show).setOnClickListener(listener)
    }

    var listener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .customAnimator(RotateAnimator())
                .asConfirm(
                    "演示自定义动画",
                    "当前的动画是一个自定义的旋转动画，无论是自定义弹窗还是自定义动画，已经被设计得非常简单；这个动画代码只有6行即可完成！",
                    null
                )
                .show()
        }
    }


    internal class RotateAnimator : PopupAnimator() {
        override fun initAnimator() {
            targetView!!.setScaleX(0f)
            targetView!!.setScaleY(0f)
            targetView!!.setAlpha(0f)
            targetView!!.setRotation(360f)
        }

        override fun animateShow() {
            targetView!!.animate().rotation(0f)
                .scaleX(1f).scaleY(1f).alpha(1f).setInterpolator(FastOutSlowInInterpolator())
                .setDuration(340)
                .start()
        }

        override fun animateDismiss() {
            targetView!!.animate().rotation(720f).scaleX(0f).scaleY(0f).alpha(0f)
                .setInterpolator(FastOutSlowInInterpolator()).setDuration(340)
                .start()
        }
    }
}
