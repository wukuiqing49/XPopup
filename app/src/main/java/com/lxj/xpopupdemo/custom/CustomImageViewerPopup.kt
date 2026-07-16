package com.lxj.xpopupdemo.custom

import android.content.Context
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.ImageViewerPopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义大图浏览弹窗
 * Create by dance, at 2019/5/8
 */
class CustomImageViewerPopup(context: Context) : ImageViewerPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_image_viewer_popup

    override fun onCreate() {
        super.onCreate()
        //        tv_pager_indicator.setVisibility(GONE);
        findViewById<View?>(R.id.tvClickMe).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(getContext())
                    .asBottomList("提示", arrayOf<String>("保存照片"), object : OnSelectListener {
                        override fun onSelect(position: Int, text: String?) {
                            ToastUtils.showLong("你自己实现保存照片")
                        }
                    }).show()
            }
        })
    }

    override fun onShow() {
        super.onShow()
        Log.e("tag", "CustomImageViewerPopup onShow")
    }

    override fun onDismiss() {
        super.onDismiss()
        Log.e("tag", "CustomImageViewerPopup onDismiss")
    }
}
