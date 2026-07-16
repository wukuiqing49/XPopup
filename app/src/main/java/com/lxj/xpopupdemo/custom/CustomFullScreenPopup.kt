package com.lxj.xpopupdemo.custom

import android.content.Context
import android.util.Log
import com.lxj.xpopup.impl.FullScreenPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义全屏弹窗
 * Create by lxj, at 2019/3/12
 */
class CustomFullScreenPopup(context: Context) : FullScreenPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_fullscreen_popup

    override fun onShow() {
        super.onShow()
        Log.e("tag", "CustomFullScreenPopup onShow")
    }

    override fun onDismiss() {
        super.onDismiss()
        Log.e("tag", "CustomFullScreenPopup onDismiss")
    }
}
