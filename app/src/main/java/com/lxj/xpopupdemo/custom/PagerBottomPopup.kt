package com.lxj.xpopupdemo.custom

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义带有ViewPager的Bottom弹窗
 * Create by dance, at 2019/5/5
 */
class PagerBottomPopup(context: Context) : BottomPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_view_pager

    var pager: ViewPager? = null

    override fun onCreate() {
        super.onCreate()
        pager = findViewById<ViewPager>(R.id.pager)
        val activity = getContext() as FragmentActivity
        pager!!.setAdapter(PAdapter(activity.getSupportFragmentManager()))

        //        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) getPopupContentView().getLayoutParams();
//        params.bottomMargin = 200;
//        getPopupContentView().setLayoutParams(params);
    }

    override val internalFragmentNames: MutableList<String?>
        get() = arrayListOf(TestFragment::class.java.simpleName)

    override fun onShow() {
        super.onShow()
    }

    override fun onDismiss() {
        super.onDismiss()
    }

    override val maxHeight: Int


        get() = (XPopupUtils.getAppHeight(getContext()) * .85f).toInt()
}
