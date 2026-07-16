package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.View
import android.widget.TextView
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义背景的Attach弹窗
 * Create by lxj, at 2019/3/13
 */
class CustomAttachPopup2(context: Context) : AttachPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_attach_popup2

    override fun onCreate() {
        super.onCreate()
        val tv = findViewById<TextView>(R.id.tv)
        tv.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
//                tv.setText(tv.getText() + "\n 啊哈哈哈啊哈");
//                tv.setText("\n 啊哈哈哈啊哈");
            }
        })
    } //    @Override
    //    protected int getPopupWidth() {
    //        return XPopupUtils.getAppWidth(getContext());
    //    }
}
