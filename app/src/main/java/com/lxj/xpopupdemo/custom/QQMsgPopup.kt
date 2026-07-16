package com.lxj.xpopupdemo.custom

import android.content.Context
import com.lxj.xpopup.core.PositionPopupView
import com.lxj.xpopup.enums.DragOrientation
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义自由定位Position弹窗
 * Create by dance, at 2019/6/14
 */
class QQMsgPopup(context: Context) : PositionPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.popup_qq_msg

    override val dragOrientation: DragOrientation


        get() = DragOrientation.DragToLeft
}
