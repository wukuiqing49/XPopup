package com.lxj.xpopupdemo.fragment

import android.view.View
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopupdemo.DemoActivity
import com.lxj.xpopupdemo.R

/**
 * 演示传入Fragment的Lifecycle，当Fragment销毁时，弹窗自动销毁，无内存泄漏
 */
class FragmentLifecycleDemo : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_lifecycle_demo

    public override fun init(view: View) {
        view.findViewById<View?>(R.id.btnShow).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(requireContext())
                    .customHostLifecycle(lifecycle)
                    .setPopupCallback(object : SimpleCallback() {
                        override fun onDismiss(popupView: BasePopupView?) {
                        }
                    })
                    .asConfirm(
                        "演示自定义UI生命周期",
                        "3秒后当前Fragment会被销毁，弹窗也自动销毁，避免内存泄漏",
                        OnConfirmListener {}).show()
                (getActivity() as DemoActivity).delayDestroy()
            }
        })
    }
}
