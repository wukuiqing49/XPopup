package com.lxj.xpopupdemo.custom

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.lxj.xpopup.impl.PartShadowPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义局部阴影弹窗
 * Create by dance, at 2018/12/21
 */
class CustomPartShadowPopupView(context: Context) : PartShadowPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_part_shadow_popup

    var text: TextView? = null
    override fun onCreate() {
        super.onCreate()
        text = findViewById<TextView>(R.id.text)
        Log.e("tag", "CustomPartShadowPopupView onCreate")
        findViewById<View?>(R.id.btnClose).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                dismiss()
            }
        })
        findViewById<View?>(R.id.ch).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                text!!.setText(text!!.getText().toString() + "\n 啦啦啦啦啦啦")
            }
        })
    }

    override fun onShow() {
        super.onShow()
        Log.e("tag", "CustomPartShadowPopupView onShow")
    }

    override fun onDismiss() {
        super.onDismiss()
        Log.e("tag", "CustomPartShadowPopupView onDismiss")
    }
}
