package com.lxj.xpopupdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.RomUtils
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.fragment.AllAnimatorDemo
import com.lxj.xpopupdemo.fragment.CustomAnimatorDemo
import com.lxj.xpopupdemo.fragment.CustomPopupDemo
import com.lxj.xpopupdemo.fragment.ImageViewerDemo
import com.lxj.xpopupdemo.fragment.PartShadowDemo
import com.lxj.xpopupdemo.fragment.QuickStartDemo
import com.lxj.xpopupdemo.util.applyEdgeToEdgeInsets

class MainActivity : AppCompatActivity() {
    var pageInfos: Array<PageInfo> = arrayOf(
        PageInfo("快速开始", QuickStartDemo()),
        PageInfo("局部阴影", PartShadowDemo()),
        PageInfo("图片浏览", ImageViewerDemo()),
        PageInfo("尝试不同动画", AllAnimatorDemo()),
        PageInfo("自定义弹窗", CustomPopupDemo()),
        PageInfo("自定义动画", CustomAnimatorDemo())
    )

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyEdgeToEdgeInsets()

        //        BarUtils.setStatusBarLightMode(this, false);
//        BarUtils.setNavBarColor(this, Color.RED);
//        BarUtils.setStatusBarVisibility();
//        BarUtils.setNavBarColor(this, Color.parseColor("#333333"));
//        BarUtils.setNavBarLightMode(this, true);
//        BarUtils.setNavBarVisibility(MainActivity.this, false);
        val actionBar = getSupportActionBar()
        actionBar!!.setTitle(actionBar.getTitle().toString() + "-" + BuildConfig.VERSION_NAME)

        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        viewPager!!.setAdapter(MainAdapter(getSupportFragmentManager()))
        tabLayout!!.setupWithViewPager(viewPager)
        XPopup.primaryColor = getResources().getColor(R.color.colorPrimary)
        //        XPopup.setAnimationDuration(400);
//        XPopup.setIsLightStatusBar(false);
//        XPopup.setPrimaryColor(Color.RED);
//        XPopup.setIsLightStatusBar(true);
//        XPopup.setNavigationBarColor(Color.RED);
        val loadingPopupView = XPopup.Builder(this)
            .isDestroyOnDismiss(true)
            .asLoading(null, LoadingPopupView.Style.ProgressBar).show()

        loadingPopupView.delayDismiss(1200)


        //        new XPopup.Builder(this).asConfirm("asda", "dasdadas", null).show();
        val str =
            (RomUtils.getRomInfo().toString() + " " + "deviceHeight：" + XPopupUtils.getScreenHeight(
                this@MainActivity
            )
                    + "  getAppHeight: " + XPopupUtils.getAppHeight(this@MainActivity)
                    + " deviceWidth: " + XPopupUtils.getScreenWidth(this@MainActivity)
                    + " getAppWidth: " + XPopupUtils.getAppWidth(this@MainActivity)
                    + "  statusHeight: " + XPopupUtils.getStatusBarHeight(getWindow())
                    + "  navHeight: " + XPopupUtils.getNavBarHeight(getWindow()))
        //                + "  hasNav: " + XPopupUtils.isNavBarVisible(getWindow());
        Log.d("tag", str)
    }

    internal inner class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(i: Int): Fragment {
            return pageInfos[i].fragment
        }

        override fun getCount(): Int = pageInfos.size

        override fun getPageTitle(position: Int): CharSequence? {
            return pageInfos[position].title
        }
    }
}
