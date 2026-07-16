package com.lxj.xpopupdemo.custom

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopupdemo.R

/**
 * Description: 自定义带有ViewPager的Drawer弹窗
 * Create by dance, at 2019/5/5
 */
class PagerDrawerPopup(context: Context) : DrawerPopupView(context) {
    override val implLayoutId: Int

        get() = R.layout.custom_pager_drawer

    var tabLayout: TabLayout? = null
    var pager: ViewPager? = null
    var titles: Array<String?> = arrayOf<String?>("首页", "娱乐", "汽车", "八卦", "搞笑", "互联网")
    override fun onCreate() {
        super.onCreate()
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        pager = findViewById<ViewPager>(R.id.pager)
        pager!!.setOffscreenPageLimit(titles.size)
        val activity = getContext() as FragmentActivity
        pager!!.setAdapter(PAdapter(activity.getSupportFragmentManager(), titles))
        tabLayout!!.setupWithViewPager(pager)
    }

    override val internalFragmentNames: MutableList<String?>
        get() = arrayListOf(TestFragment::class.java.simpleName)

    override fun onShow() {
        super.onShow()
        Log.e("tag", "PagerDrawerPopup onShow")
    }

    override fun onDismiss() {
        super.onDismiss()
        Log.e("tag", "PagerDrawerPopup onDismiss")
    }
}
