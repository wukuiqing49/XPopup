package com.lxj.xpopupdemo.custom

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.XPopupApp

/**
 * Description: 自定义Attach弹窗，水平方向的带气泡的弹窗
 * Create by lxj, at 2019/3/13
 */
class CustomHorizontalBubbleAttachPopup(context: Context) :
    BubbleHorizontalAttachPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_attach_popup

    override fun onCreate() {
        super.onCreate()
        setBubbleBgColor(Color.parseColor("#4D5063"))
        setBubbleShadowSize(XPopupUtils.dp2px(getContext(), 3f))
        setBubbleShadowColor(Color.BLACK)
        popupImplView.setBackgroundResource(0)
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
