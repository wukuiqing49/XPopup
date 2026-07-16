package com.lxj.xpopupdemo.fragment

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopupdemo.R

/**
 * Description:
 * Create by dance, at 2018/12/9
 */
class AllAnimatorDemo : BaseFragment() {
    var spinner: Spinner? = null
    override val layoutId: Int = R.layout.fragment_all_animator_demo

    var data: Array<PopupAnimation?> = emptyArray()
    override fun init(view: View) {
        spinner = view.findViewById<Spinner>(R.id.spinner)

        data = PopupAnimation.entries.toTypedArray()
        spinner!!.setAdapter(
            ArrayAdapter<PopupAnimation?>(
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
                        XPopup.Builder(requireContext())
                            .popupAnimation(data[position])
                            .asConfirm(
                                "演示应用不同的动画",
                                "你可以为弹窗选择任意一种动画，但这并不必要，因为我已经默认给每种弹窗设定了最佳动画！对于你自定义的弹窗，可以随心选择心仪的动画方案。",
                                null
                            )
                            .show()
                    }
                }, 200) //确保spinner的消失动画不影响XPopup动画，可以看得更清晰
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }
}
