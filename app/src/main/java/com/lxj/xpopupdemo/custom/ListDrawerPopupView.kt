package com.lxj.xpopupdemo.custom

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.vm.DemoVM
import java.util.Random

/**
 * Description: 自定义带列表的Drawer弹窗
 * Create by dance, at 2019/1/9
 */
class ListDrawerPopupView(context: Context) : DrawerPopupView(context) {
    var recyclerView: RecyclerView? = null
    override val implLayoutId: Int

        get() = R.layout.custom_list_drawer

    val data: ArrayList<String?> = ArrayList<String?>()

    var demoVM: DemoVM? = null
    override fun onCreate() {
        demoVM =
            ViewModelProvider(((getContext() as FragmentActivity?)!!)).get<DemoVM>(DemoVM::class.java)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.setLayoutManager(LinearLayoutManager(getContext()))

        for (i in 0..49) {
            data.add("" + i)
        }

        val button = findViewById<Button>(R.id.btn)
        val commonAdapter: EasyAdapter<String?> =
            object : EasyAdapter<String?>(data, android.R.layout.simple_list_item_1) {
                override fun bind(holder: ViewHolder, s: String?, position: Int) {
                    holder.setText(android.R.id.text1, s.orEmpty())
                }
            }
        demoVM!!.liveData.observe(this, object : Observer<String?> {
            override fun onChanged(s: String?) {
                button.setText(s.orEmpty())
                Toast.makeText(getContext(), "弹窗onResume时才收到数据更新", Toast.LENGTH_SHORT)
                    .show()
                Log.e("tag", "liveData onChange: " + s)
            }
        })
        recyclerView!!.setAdapter(commonAdapter)
        button.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
//                if(data.size()==0)return;
//                data.remove(0);
//                commonAdapter.notifyDataSetChanged();
                dismiss()
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        demoVM!!.liveData.postValue(Random().nextInt(10000).toString() + "")
                    }
                }, 1000)
            }
        })
    }

    override val maxWidth: Int


        get() = XPopupUtils.getScreenWidth(getContext()) - 100
}
