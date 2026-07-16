package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.View
import android.widget.Toast
import com.lxj.xpopup.core.HorizontalAttachPopupView
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.XPopupApp

/**
 * Description: 自定义Attach弹窗，水平方向的
 * Create by lxj, at 2019/3/13
 */
class CustomAttachPopup(context: Context) : HorizontalAttachPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_attach_popup

    override fun onCreate() {
        super.onCreate()
        findViewById<View?>(R.id.tv_zan).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(XPopupApp.context, "赞", Toast.LENGTH_LONG).show()
                dismiss()
            }
        })
        findViewById<View?>(R.id.tv_comment).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(XPopupApp.context, "评论", Toast.LENGTH_LONG).show()
                dismiss()
            }
        })
    }
}
