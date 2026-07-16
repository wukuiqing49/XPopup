package com.lxj.xpopupdemo.custom

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopup.widget.VerticalRecyclerView
import com.lxj.xpopupdemo.R

/**
 * Description: 仿知乎底部评论弹窗
 * Create by dance, at 2018/12/25
 */
class ZhihuCommentPopup(context: Context) : BottomPopupView(context) {
    var recyclerView: VerticalRecyclerView? = null
    private var data: ArrayList<String?>? = null
    private var commonAdapter: EasyAdapter<String?>? = null

    override val implLayoutId: Int


        get() = R.layout.custom_bottom_popup

    override fun onCreate() {
        super.onCreate()
        recyclerView = findViewById<VerticalRecyclerView>(R.id.recyclerView)
        findViewById<View?>(R.id.tv_temp).setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                //弹出新的弹窗用来输入
                val textBottomPopup = CustomEditTextBottomPopup(getContext())
                XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    .autoFocusEditText(true)
                    .setPopupCallback(object : SimpleCallback() {
                        override fun onShow(popupView: BasePopupView?) {
                        }

                        override fun onDismiss(popupView: BasePopupView?) {
                            val comment = textBottomPopup.comment
                            if (!comment.isEmpty()) {
                                data!!.add(0, comment)
                                commonAdapter!!.notifyDataSetChanged()
                            }
                        }
                    })
                    .asCustom(textBottomPopup)
                    .show()
            }
        })

        data = ArrayList<String?>()
        for (i in 0..14) {
            data!!.add("这是一个自定义Bottom类型的弹窗！你可以在里面添加任何滚动的View，我已经智能处理好嵌套滚动，你只需编写UI和逻辑即可！")
        }
        commonAdapter = object : EasyAdapter<String?>(data!!, R.layout.adapter_zhihu_comment) {
            override fun bind(holder: ViewHolder, s: String?, position: Int) {
                holder.setText(R.id.name, "知乎大神 - " + position)
                    .setText(R.id.comment, s.orEmpty())
                holder.getView<View>(R.id.btnDel).setOnClickListener(object : OnClickListener {
                    override fun onClick(v: View?) {
                        this@ZhihuCommentPopup.data!!.removeAt(position)
                        commonAdapter!!.notifyItemRemoved(position)
                        commonAdapter!!.notifyItemRangeChanged(position, data!!.size)
                    }
                })
            }
        }
        commonAdapter!!.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                //不要直接这样做，会导致消失动画未执行完就跳转界面，不流畅。
//                dismiss();
//                getContext().startActivity(new Intent(getContext(), DemoActivity.class))
                //可以等消失动画执行完毕再开启新界面
//                dismissWith(new Runnable() {
//                    @Override
//                    public void run() {
//                        getContext().startActivity(new Intent(getContext(), DemoActivity.class));
//                    }
//                });
            }
        })
        recyclerView!!.setAdapter(commonAdapter)
    }

    //完全可见执行
    override fun onShow() {
        super.onShow()
        Log.e("tag", "知乎评论 onShow")
    }

    //完全消失执行
    override fun onDismiss() {
        Log.e("tag", "知乎评论 onDismiss")
    }

    override val maxHeight: Int


        get() = (XPopupUtils.getScreenHeight(getContext()) * .7f).toInt()

    override fun onBackPressed(): Boolean {
        Toast.makeText(getContext(), "拦截返回", Toast.LENGTH_SHORT).show()
        return true
    }
}
