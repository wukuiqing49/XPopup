package com.lxj.xpopupdemo.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.View.OnLongClickListener
import androidx.recyclerview.widget.RecyclerView
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupPosition
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopup.widget.VerticalRecyclerView
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.custom.CustomDrawerPopupView
import com.lxj.xpopupdemo.custom.CustomPartShadowPopupView
import com.lxj.xpopupdemo.custom.CustomPartShadowPopupView2

/**
 * Description: 局部阴影的示例
 * Create by dance, at 2018/12/21
 */
class PartShadowDemo : BaseFragment(), View.OnClickListener {
    var ll_container: View? = null
    var recyclerView: VerticalRecyclerView? = null
    private var popupView: CustomPartShadowPopupView? = null

    private var drawerPopupView: CustomDrawerPopupView? = null

    override val layoutId: Int
        get() = R.layout.fragment_part_shadow_demo

    public override fun init(view: View) {
        ll_container = view.findViewById<View?>(R.id.ll_container)
        recyclerView = view.findViewById<VerticalRecyclerView>(R.id.recyclerView)

        view.findViewById<View?>(R.id.tv_all).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv_price).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv_sales).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv_select).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv_filter).setOnClickListener(this)
        view.findViewById<View?>(R.id.tvCenter).setOnClickListener(this)
        view.findViewById<View?>(R.id.tvCenter2).setOnClickListener(this)

        drawerPopupView = CustomDrawerPopupView(requireContext())

        val data = ArrayList<String?>()
        for (i in 0..49) {
            data.add(i.toString() + "")
        }
        val adapter: EasyAdapter<String?> =
            object : EasyAdapter<String?>(data, android.R.layout.simple_list_item_1) {
                override fun bind(holder: ViewHolder, s: String?, position: Int) {
                    holder.convertView.setBackgroundColor(Color.parseColor("#fafafa"))
                    holder.setText(android.R.id.text1, "长按我试试 - " + position)
                    //必须要在事件发生之前就watch
                    val builder = XPopup.Builder(requireContext())
                        .hasShadowBg(false).watchView(holder.convertView)
                    holder.convertView.setOnLongClickListener(object : OnLongClickListener {
                        override fun onLongClick(v: View?): Boolean {
                            builder.asAttachList(
                                arrayOf<String>("置顶", "编辑", "删除"),
                                null,
                                object : OnSelectListener {
                                    override fun onSelect(position: Int, text: String?) {
                                        toast(text)
                                    }
                                }).show()
                            return true
                        }
                    })
                }
            }
        adapter.setOnItemClickListener(object : SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                toast(data.get(position))
            }
        })
        recyclerView!!.setupDivider(false)
        recyclerView!!.setAdapter(adapter)
    }

    private fun showPartShadow(v: View?) {
        if (popupView == null) {
            popupView = XPopup.Builder(requireContext())
                .atView(v) //                    .isClickThrough(true)
                //                    .isViewMode(true)
                //                    .isRequestFocus(false)
                //                    .isTouchThrough(true)
                //                    .notDismissWhenTouchInView(view.findViewById(R.id.tv_select))
                //                    .isCenterHorizontal(true)
                .autoOpenSoftInput(true) //                    .offsetY(250)
                //                    .offsetX(100)
                .setPopupCallback(object : SimpleCallback() {
                    override fun onShow(popupView: BasePopupView?) {
                        toast("显示了")
                    }

                    override fun onDismiss(popupView: BasePopupView?) {
                    }
                })
                .asCustom(CustomPartShadowPopupView(requireContext())) as CustomPartShadowPopupView?
        }

        popupView!!.show()
    }

    var popupView2: CustomPartShadowPopupView2? = null

    override fun onClick(v: View) {
        val id = v.getId()
        if (id == R.id.tv_all || id == R.id.tv_price || id == R.id.tv_sales) {
            showPartShadow(v)
        } else if (id == R.id.tv_filter) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .popupPosition(PopupPosition.Right)
                .asCustom(drawerPopupView!!)
                .show()
        } else if (id == R.id.tv_select) {
            XPopup.Builder(requireContext())
                .atView(v)
                .autoOpenSoftInput(true)
                .moveUpToKeyboard(false)
                .asCustom(CustomPartShadowPopupView(requireContext()))
                .show()
        } else if (id == R.id.tvCenter) {
            XPopup.Builder(requireContext())
                .atView(v)
                .isViewMode(true)
                .popupPosition(PopupPosition.Top)
                .asCustom(CustomPartShadowPopupView2(requireContext(), Gravity.START))
                .show()
        } else if (id == R.id.tvCenter2) {
            if (popupView2 == null) {
                popupView2 = CustomPartShadowPopupView2(requireContext(), Gravity.END)
            }
            XPopup.Builder(requireContext())
                .atView(v)
                .isViewMode(true)
                .popupPosition(PopupPosition.Bottom)
                .asCustom(popupView2!!)
                .show()
        }
    }
}
