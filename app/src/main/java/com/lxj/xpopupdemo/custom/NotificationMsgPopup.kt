package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.View
import com.blankj.utilcode.util.ScreenUtils
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义自由定位Position弹窗
 * Create by dance, at 2019/6/14
 */
class NotificationMsgPopup(context: Context) : PositionPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.popup_notification_msg

    override fun onCreate() {
        super.onCreate()
        findViewById<View?>(R.id.tvClose).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                dismiss()
            }
        })
    }

    override val popupWidth: Int


        get() = ScreenUtils.getScreenWidth()
}
