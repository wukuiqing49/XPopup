package com.lxj.xpopupdemo

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopupdemo.fragment.FragmentLifecycleDemo
import com.lxj.xpopupdemo.fragment.ImageViewerDemo.ImageAdapter
import com.lxj.xpopupdemo.util.applyEdgeToEdgeInsets

/**
 * Description:
 * Create by lxj, at 2019/2/2
 */
class DemoActivity : AppCompatActivity() {
    var editText: EditText? = null
    var recyclerView: RecyclerView? = null
    var attachPopup: BasePopupView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        applyEdgeToEdgeInsets()
        editText = findViewById<EditText>(R.id.et)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        findViewById<View?>(R.id.btnShowFragment).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showFragment()
            }
        })
        findViewById<View?>(R.id.text).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showMultiPopup()
            }
        })
        showMultiPopup()

        attachPopup = XPopup.Builder(this)
            .atView(editText)
            .dismissOnTouchOutside(false)
            .isViewMode(true) //开启View实现
            .isRequestFocus(false) //不强制焦点
            .isTouchThrough(true)
            .hasShadowBg(false)
            .positionByWindowCenter(true)
            .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
            .asAttachList(
                arrayOf<String>(
                    "联想到的内容 - 1",
                    "联想到的内容 - 2",
                    "联想到的内容 - 333"
                ), null, object : OnSelectListener {
                    override fun onSelect(position: Int, text: String?) {
                        Toast.makeText(XPopupApp.context, text, Toast.LENGTH_LONG).show()
                    }
                })
        editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) {
                    attachPopup!!.dismiss()
                    return
                }
                if (attachPopup!!.isDismiss) {
                    attachPopup!!.show()
                }
            }
        })

        initData()
    }

    private fun initData() {
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView!!.setAdapter(ImageAdapter())
        showFragment()
    }

    fun showMultiPopup() {
        val loadingPopup: BasePopupView = XPopup.Builder(this).asLoading()
        loadingPopup.show()
        XPopup.Builder(this@DemoActivity)
            .autoDismiss(false)
            .asBottomList(
                "haha",
                arrayOf<String>("点我显示弹窗", "点我显示弹窗", "点我显示弹窗", "点我显示弹窗"),
                object : OnSelectListener {
                    override fun onSelect(position: Int, text: String?) {
                        XPopup.Builder(this@DemoActivity)
                            .asConfirm("测试", "aaaa", object : OnConfirmListener {
                                override fun onConfirm() {
                                    loadingPopup.dismiss()
                                }
                            }).show()
                    }
                }).show()
    }

    var fragmentLifecycleDemo: FragmentLifecycleDemo? = null
    fun showFragment() {
        fragmentLifecycleDemo = FragmentLifecycleDemo()
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flFragment, fragmentLifecycleDemo!!)
            .commitNow()
    }

    private val handler = Handler()
    fun delayDestroy() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (fragmentLifecycleDemo == null) return
                getSupportFragmentManager().beginTransaction().remove(fragmentLifecycleDemo!!)
                    .commitNow()
                fragmentLifecycleDemo = null
            }
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
