package com.lxj.xpopupdemo.custom

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.widget.VerticalRecyclerView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义抽屉弹窗
 * Create by dance, at 2018/12/20
 */
class CustomDrawerPopupView(context: Context) : DrawerPopupView(context) {
    var text: TextView? = null

    override val implLayoutId: Int


        get() = R.layout.custom_drawer_popup2

    override fun onCreate() {
        super.onCreate()
        //        CustomDrawerPopup2Binding.bind(getPopupImplView());
        Log.e("tag", "CustomDrawerPopupView onCreate")

        //        text = findViewById(R.id.text);
//        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        //通过设置topMargin，可以让Drawer弹窗进行局部阴影展示
//        setPadding(0, 400, 0, 0);
        val rv = findViewById<VerticalRecyclerView>(R.id.rv)
        val list: ArrayList<String?> = ArrayList()
        for (i in 0..199) {
            list.add(i.toString() + "")
        }
        rv.setAdapter(object : EasyAdapter<String?>(list, R.layout.temp) {
            override fun bind(viewHolder: ViewHolder, o: String?, i: Int) {
                if (i % 2 == 0) {
                    viewHolder.getView<TextView>(R.id.text).setText("aa - " + i)
                    viewHolder.getView<TextView>(R.id.text).setBackgroundColor(Color.WHITE)
                } else {
                    viewHolder.getView<TextView>(R.id.text).setText(
                        "aa - " + i + "大萨达所撒多" +
                                "\n大萨达所撒多大萨达所撒多"
                    )
                    viewHolder.getView<TextView>(R.id.text).setBackgroundColor(Color.RED)
                }
            }
        })
        findViewById<View?>(R.id.btnMe).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(getContext()).isDestroyOnDismiss(true)
                    .asConfirm("提示", "确定要退出吗？", object : OnConfirmListener {
                        override fun onConfirm() {
                            (getContext() as Activity).finish()
                            dismiss()
                        }
                    }).show()
            }
        })
    }

    override fun onShow() {
        super.onShow()
        //        text.setText(new Random().nextInt()+"");
        Log.e("tag", "CustomDrawerPopupView onShow")
    }

    override fun onDismiss() {
        super.onDismiss()
        Log.e("tag", "CustomDrawerPopupView onDismiss")
    }
}
