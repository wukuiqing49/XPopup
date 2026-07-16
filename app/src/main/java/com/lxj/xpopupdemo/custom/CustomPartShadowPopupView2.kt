package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.View
import com.lxj.xpopup.impl.PartShadowPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义局部阴影弹窗
 * Create by dance, at 2018/12/21
 */
class CustomPartShadowPopupView2(context: Context, var gravity: Int) :
    PartShadowPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_part_shadow_popup2

    override fun onCreate() {
        super.onCreate()
        val params = findViewById<View?>(R.id.ll).getLayoutParams() as LayoutParams
        params.gravity = gravity
    }
}
