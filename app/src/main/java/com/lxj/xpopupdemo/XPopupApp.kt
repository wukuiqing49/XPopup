package com.lxj.xpopupdemo

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils

/**
 * Description:
 * Create by dance, at 2019/1/1
 */
class XPopupApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.getDefaultMaker().setBgResource(R.drawable.bg_toast)
        ToastUtils.getDefaultMaker().setTextColor(Color.WHITE)
    }

    companion object {
        var context: Context? = null
    }
}
