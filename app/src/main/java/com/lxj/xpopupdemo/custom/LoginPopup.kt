package com.lxj.xpopupdemo.custom

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopupdemo.R

class LoginPopup(context: Context) : CenterPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.popup_login

    override fun onCreate() {
        super.onCreate()
        val button = findViewById<Button>(R.id.btnSelect)
        button.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .isRequestFocus(false)
                    .atView(v)
                    .asAttachList(
                        arrayOf<String>("1", "2", "3", "4"),
                        null,
                        object : OnSelectListener {
                            override fun onSelect(position: Int, text: String?) {
                            }
                        }).show()
            }
        })
        val etName = findViewById<EditText>(R.id.etName)
        etName.setOnKeyListener(object : OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Toast.makeText(getContext(), "按了删除键", Toast.LENGTH_SHORT).show()
                    return true
                }
                return false
            }
        })
    }
}
