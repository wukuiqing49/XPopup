package com.lxj.xpopupdemo.fragment

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.core.CenterPopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopupdemo.R

/**
 * Description:
 * Create by dance, at 2018/12/9
 */
class CustomPopupDemo : BaseFragment() {
    var spinner: Spinner? = null
    var temp: TextView? = null

    override val layoutId: Int
        get() = R.layout.fragment_all_animator_demo

    var data: Array<PopupAnimation> = emptyArray()

    public override fun init(view: View) {
        spinner = view.findViewById<Spinner>(R.id.spinner)
        temp = view.findViewById<TextView>(R.id.temp)
        temp!!.setText("演示如何自定义弹窗，并给自定义的弹窗应用不同的内置动画方案；你也可以为自己的弹窗编写自定义的动画。")

        data = PopupAnimation.values()
        spinner!!.setAdapter(
            ArrayAdapter<PopupAnimation>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                data
            )
        )

        spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinner!!.postDelayed(object : Runnable {
                    override fun run() {
                        val customPopup = CustomPopup(requireContext())
                        XPopup.Builder(requireContext())
                            .popupAnimation(data[position])
                            .autoOpenSoftInput(true)
                            .asCustom(customPopup)
                            .show()
                    }
                }, 200) //确保spinner的消失动画不影响XPopup动画，可以看得更清晰
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }


    class CustomPopup(context: Context) : CenterPopupView(context) {
        override val implLayoutId: Int

            get() = R.layout.custom_popup

        override fun onCreate() {
            super.onCreate()
            findViewById<View?>(R.id.tv_close).setOnClickListener(object : OnClickListener {
                override fun onClick(v: View?) {
                    dismiss()
                }
            })
        } //        @Override
        //        protected int getMaxHeight() {
        //            return 200;
        //        }
        //
        //        @Override
        //        protected int getMaxWidth() {
        //            return 1000;
        //        }
    }

    internal class CustomPopup2(context: Context) : BottomPopupView(context) {
        var recyclerView: RecyclerView? = null

        override val implLayoutId: Int


            get() = R.layout.custom_popup2

        override fun onCreate() {
            super.onCreate()
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView!!.setLayoutManager(LinearLayoutManager(context))
            val data = ArrayList<String?>()
            for (i in 0..2) {
                data.add("" + i)
            }

            recyclerView!!.setAdapter(object :
                EasyAdapter<String?>(data, android.R.layout.simple_list_item_1) {
                override fun bind(holder: ViewHolder, s: String?, position: Int) {
                    holder.setText(android.R.id.text1, s.orEmpty())
                }
            })
        } //        @Override
        //        protected int getMaxHeight() {
        //            return 1200;
        //        }
        //
        //返回0表示让宽度撑满window，或者你可以返回一个任意宽度
        //        @Override
        //        protected int getMaxWidth() {
        //            return 1200;
        //        }
    }
}
