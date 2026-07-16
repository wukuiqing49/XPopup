package com.lxj.xpopupdemo.custom

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lxj.xpopup.core.BubbleAttachPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义气泡Attach弹窗
 * Create by lxj, at 2019/3/13
 */
class CustomBubbleAttachPopup(context: Context) : BubbleAttachPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_bubble_attach_popup

    override fun onCreate() {
        super.onCreate()
        setBubbleBgColor(Color.BLUE)
        setBubbleShadowSize(XPopupUtils.dp2px(getContext(), 6f))
        setBubbleShadowColor(Color.RED)
        setArrowWidth(XPopupUtils.dp2px(getContext(), 8f))
        setArrowHeight(XPopupUtils.dp2px(getContext(), 9f))
        //                                .setBubbleRadius(100)
        setArrowRadius(XPopupUtils.dp2px(getContext(), 2f))
        val tv = findViewById<TextView>(R.id.tv)
        Glide.with(getContext()).load("https://t7.baidu.com/it/u=963301259,1982396977&fm=193&f=GIF")
            .into((findViewById<android.view.View?>(R.id.image) as android.widget.ImageView?)!!)
        tv.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
//                tv.setText(tv.getText() + "\n 啊哈哈哈啊哈");
//                tv.setText("\n 啊哈哈哈啊哈");
                dismiss()
            }
        })
    }
}
