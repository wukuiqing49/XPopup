package com.lxj.xpopupdemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.lxj.statelayout.StateLayout
import com.lxj.xpopupdemo.XPopupApp

/**
 * Description:
 * Create by dance, at 2018/12/9
 */
abstract class BaseFragment : Fragment() {
    var rootView: View? = null
    var isInit: Boolean = false
    var stateLayout: StateLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(this.layoutId, container, false)
            stateLayout = StateLayout(requireContext()).wrap(rootView).showLoading()
        }
        return stateLayout
    }

    override fun onResume() {
        super.onResume()
        safeInit()
    }

    private fun safeInit() {
        if (getUserVisibleHint() && rootView != null) {
            if (!isInit) {
                isInit = true
                init(rootView!!)
                stateLayout!!.postDelayed(object : Runnable {
                    override fun run() {
                        stateLayout!!.showContent()
                    }
                }, 300)
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        safeInit()
    }

    protected abstract val layoutId: Int
    abstract fun init(view: View)

    fun toast(msg: String?) {
        Toast.makeText(XPopupApp.context, msg, Toast.LENGTH_SHORT).show()
    }
}
