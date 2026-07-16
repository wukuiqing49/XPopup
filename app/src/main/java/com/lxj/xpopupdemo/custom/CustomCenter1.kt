package com.lxj.xpopupdemo.custom

import android.content.Context
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopupdemo.R

class CustomCenter1(context: Context) : CenterPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.temp2

    override val maxWidth: Int


        get() = 0
}
