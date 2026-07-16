package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.View
import android.widget.EditText
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义带有输入框的Bottom弹窗
 * Create by dance, at 2019/2/27
 */
class CustomEditTextBottomPopup(context: Context) : BottomPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_edittext_bottom_popup

    override fun onCreate() {
        super.onCreate()
        findViewById<View?>(R.id.btn_finish).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                dismiss()
            }
        })
        //
//        setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode==KeyEvent.KEYCODE_BACK){
//                    ToastUtils.showShort("自定义弹窗设置了KeyListener");
//                    return true;
//                }
//                return true;
//            }
//        });
    }

    override fun onShow() {
        super.onShow()
    }

    override fun onDismiss() {
        super.onDismiss()
        //        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }

    val comment: String
        get() {
            val et = findViewById<EditText>(R.id.et_comment)
            return et.getText().toString()
        } //    @Override
    //    protected int getMaxHeight() {
    //        return (int) (XPopupUtils.getWindowHeight(getContext())*0.75);
    //    }
}
