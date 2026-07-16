package com.lxj.xpopupdemo.custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PAdapter : FragmentPagerAdapter {
    var titles: Array<String?>? = null

    constructor(fm: FragmentManager) : super(fm)

    constructor(fm: FragmentManager, titles: Array<String?>?) : super(fm) {
        this.titles = titles
    }


    override fun getCount(): Int = 6

    override fun getItem(position: Int): Fragment {
        return TestFragment.create("XPopup默认是Dialog实现，由于Android的限制，Dialog中默认无法使用Fragment。\n\n所以要想在弹窗中使用Fragment，要设置isViewMode(true).")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles != null) titles!![position] else "xpopup"
    }
}
