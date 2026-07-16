package com.lxj.xpopupdemo.custom

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.fragment.BaseFragment

class TestFragment : BaseFragment() {
    override val layoutId: Int = R.layout.fragment_test

    override fun init(view: View) {
        val text = requireArguments().getString("text", "XPopup")
        view.findViewById<TextView?>(R.id.tv).setText(text)
    }

    companion object {
        fun create(text: String?): TestFragment {
            val testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putString("text", text)
            testFragment.setArguments(bundle)
            return testFragment
        }
    }
}
