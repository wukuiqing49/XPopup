package com.lxj.xpopupdemo.custom

import android.content.Context
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopupdemo.R

class CustomCenterPopup(context: Context) : CenterPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.popup_custom_center
}
