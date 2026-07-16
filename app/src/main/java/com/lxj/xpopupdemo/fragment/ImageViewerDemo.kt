package com.lxj.xpopupdemo.fragment

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ConvertUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.core.ImageViewerPopupView
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener
import com.lxj.xpopupdemo.util.SmartGlideImageLoader
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.custom.CustomImageViewerPopup

/**
 * Description:
 * Create by lxj, at 2019/1/22
 */
class ImageViewerDemo : BaseFragment() {
    var url1: String =
        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2279952540,2544282724&fm=26&gp=0.jpg"
    var url2: String =
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1549382334&di=332b0aa1ec4ccd293f176164d998e5ab&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D121ef3421a38534398c28f62fb7ada0b%2Ffaf2b2119313b07eedb4502606d7912397dd8c96.jpg"

    override val layoutId: Int
        get() = R.layout.fragment_image_preview

    var recyclerView: RecyclerView? = null
    var image1: ImageView? = null
    var image2: ImageView? = null
    var pager: ViewPager? = null
    var pager2: ViewPager2? = null
    var btn_custom: Button? = null

    public override fun init(view: View) {
        view.findViewById<View?>(R.id.btnClear).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Glide.get(requireContext()).clearMemory()
                Thread(object : Runnable {
                    override fun run() {
                        Glide.get(requireContext()).clearDiskCache()
                    }
                }).start()
            }
        })
        image1 = view.findViewById<ImageView>(R.id.image1)
        image2 = view.findViewById<ImageView>(R.id.image2)
        pager = view.findViewById<ViewPager>(R.id.pager)
        pager2 = view.findViewById<ViewPager2>(R.id.pager2)
        btn_custom = view.findViewById<Button>(R.id.btn_custom)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.setLayoutManager(GridLayoutManager(requireContext(), 3))
        recyclerView!!.setAdapter(ImageAdapter())


        Glide.with(this).load(url1).into(image1!!)
        Glide.with(this).load(url2).into(image2!!)
        image1!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(requireContext())
                    .isDestroyOnDismiss(true)
                    .asImageViewer(
                        image1,
                        url1,
                        true,
                        Color.parseColor("#f1f1f1"),
                        -1,
                        0,
                        false,
                        Color.BLACK,
                        SmartGlideImageLoader(R.mipmap.ic_launcher),
                        object : OnImageViewerLongPressListener {
                            override fun onLongPressed(popupView: BasePopupView?, position: Int) {
                                Toast.makeText(
                                    requireContext(),
                                    "长按了第" + position + "个图片",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    .show()
            }
        })
        image2!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                XPopup.Builder(requireContext())
                    .asImageViewer(image2, url2, SmartGlideImageLoader())
                    .show()
            }
        })

        //ViewPager bind data
        pager!!.setOffscreenPageLimit(list.size)
        pager!!.setAdapter(ImagePagerAdapter())

        btn_custom!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //自定义的弹窗需要用asCustom来显示，之前的asImageViewer这些方法当然不能用了。
                val viewerPopup = CustomImageViewerPopup(requireContext())
                //自定义的ImageViewer弹窗需要自己手动设置相应的属性，必须设置的有srcView，url和imageLoader。
                viewerPopup.setSingleSrcView(image2, url2)
                //                viewerPopup.isInfinite(true);
                viewerPopup.setXPopupImageLoader(SmartGlideImageLoader())
                //                viewerPopup.isShowIndicator(false);//是否显示页码指示器
//                viewerPopup.isShowPlaceholder(false);//是否显示白色占位块
//                viewerPopup.isShowSaveButton(false);//是否显示保存按钮
                XPopup.Builder(requireContext())
                    .isDestroyOnDismiss(true)
                    .asCustom(viewerPopup)
                    .show()
            }
        })

        pager2!!.setAdapter(ViewPager2Adapter())
    }

    class ImageAdapter : EasyAdapter<Any?>(list, R.layout.adapter_image) {
        override fun bind(holder: ViewHolder, s: Any?, position: Int) {
            val imageView = holder.getView<ImageView>(R.id.image)
            //1. 加载图片
            Glide.with(imageView).load(s).apply(
                RequestOptions()
                    .transform(CenterCrop(), RoundedCorners(ConvertUtils.dp2px(10f)))
            )
                .error(R.mipmap.ic_launcher).into(imageView)

            //2. 设置点击
            imageView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    XPopup.Builder(holder.itemView.context) //                            .animationDuration(1000)
                        .isTouchThrough(true)
                        .asImageViewer(
                            imageView, position, list,
                            false, true, -1, -1, ConvertUtils.dp2px(10f), true,
                            Color.rgb(32, 36, 46),
                            object : OnSrcViewUpdateListener {
                                override fun onSrcViewUpdate(
                                    popupView: ImageViewerPopupView,
                                    position: Int
                                ) {
                                    val rv = holder.itemView.getParent() as RecyclerView
                                    popupView.updateSrcView(rv.getChildAt(position) as ImageView?)
                                }
                            }, SmartGlideImageLoader(true, R.mipmap.ic_launcher), null
                        )
                        .show()
                }
            })
        }
    }

    //ViewPager2的adapter
    inner class ViewPager2Adapter : EasyAdapter<Any?>(list, R.layout.adapter_image2) {
        override fun bind(holder: ViewHolder, s: Any?, position: Int) {
            val imageView = holder.getView<ImageView>(R.id.image)
            //1. 加载图片
            Glide.with(imageView).load(s).error(R.mipmap.ic_launcher).into(imageView)

            //2. 设置点击
            imageView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    XPopup.Builder(holder.itemView.context)
                        .asImageViewer(
                            imageView, position, list,
                            object : OnSrcViewUpdateListener {
                                override fun onSrcViewUpdate(
                                    popupView: ImageViewerPopupView,
                                    position: Int
                                ) {
                                    pager2!!.setCurrentItem(position, false)
                                    //一定要post，因为setCurrentItem内部实现是RecyclerView.scrollTo()，这个是异步的
                                    pager2!!.post(object : Runnable {
                                        override fun run() {
                                            //由于ViewPager2内部是包裹了一个RecyclerView，而RecyclerView始终维护一个子View
                                            val rv = pager2!!.getChildAt(0) as RecyclerView
                                            //再拿子View，就是ImageView
                                            popupView.updateSrcView(rv.getChildAt(0) as ImageView?)
                                        }
                                    })
                                }
                            }, SmartGlideImageLoader()
                        )
                        .show()
                }
            })
        }
    }

    internal inner class ImagePagerAdapter : PagerAdapter() {
        override fun getCount(): Int = list.size

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return view === o
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(container.context)
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
            container.addView(imageView)

            //1. 加载图片
            Glide.with(imageView).load(list.get(position)).into(imageView)

            //2. 设置点击
            imageView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    XPopup.Builder(requireContext())
                        .asImageViewer(
                            imageView,
                            position,
                            list,
                            true,
                            false,
                            -1,
                            -1,
                            -1,
                            true,
                            Color.BLACK,
                            object : OnSrcViewUpdateListener {
                                override fun onSrcViewUpdate(
                                    popupView: ImageViewerPopupView,
                                    position: Int
                                ) {
                                    //1.pager更新当前显示的图片
                                    //当启用isInfinite时，position会无限增大，需要映射为当前ViewPager中的页
                                    val realPosi: Int = position % list.size
                                    //                            Log.e("tag", "position: "+realPosi + " list size: "+list.size());
                                    pager!!.setCurrentItem(position, false)
                                    //2.更新弹窗的srcView，注意这里的position是list中的position，上面ViewPager设置了pageLimit数量，
                                    //保证能拿到child，如果不设置pageLimit，ViewPager默认最多维护3个page，会导致拿不到child
                                    popupView.updateSrcView(pager!!.getChildAt(position) as ImageView?)
                                }
                            },
                            SmartGlideImageLoader(),
                            null
                        )
                        .show()
                }
            })

            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }

    companion object {
        @JvmField
        val list: MutableList<Any?> = ArrayList()

        init {
            list.clear()
            list.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2279952540,2544282724&fm=26&gp=0.jpg")
            list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=851052518,4050485518&fm=26&gp=0.jpg")
            list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=174904559,2874238085&fm=26&gp=0.jpg")
            list.add("https://image.flaticon.com/icons/png/512/910/910277.png")
            list.add("https://user-gold-cdn.xitu.io/2019/1/25/168839e977414cc1?imageView2/2/w/800/q/100")
            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551692956639&di=8ee41e070c6a42addfc07522fda3b6c8&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160413%2F75659e9b05b04eb8adf5b52669394897.jpg")
            list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.love.tv%2F2017%2F10%2F15%2F23%2F3dc47bd3b80d4dfc89cfc8d74a0c44fe.gif&refer=http%3A%2F%2Fimg.love.tv&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg")
            list.add("https://word.7english.cn/user/publicNoteImage/4e44a8706ee94016a4d40ad0693e9f41/B40CF2CA54715E64CF4AA3632FD4F70E.jpg")
            list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.huabanimg.com%2F3fee54d0b2e0b7a132319a8e104f5fdc2edd3d35d03ee-93Jmdq_fw658&refer=http%3A%2F%2Fhbimg.huabanimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg")
            list.add("https://word.7english.cn/user/publicNoteImage/4e44a8706ee94016a4d40ad0693e9f41/3F8B1BFDCBA2559EB69BA1670915E912.jpg")
            list.add("https://word.7english.cn/user/publicNoteImage/4e44a8706ee94016a4d40ad0693e9f41/5C50B56D6FC9C30562FE15716B02AA3E.jpg")
            list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9f569629c4dec5ed1b603982058c6853607b1f0af685e-PcenmQ_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg")
            list.add("https://test.yujoy.com.cn:59010/file/postImage/2021/03/03/7c9114bb-bc4a-40c4-94ab-01833228f26f.png")
            list.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fgss0.baidu.com%2F94o3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2F8c1001e93901213f1820a0d956e736d12f2e95a0.jpg&refer=http%3A%2F%2Fgss0.baidu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg")
            list.add("https://img.live.qiqushiting.com/Pimg/img/squ/img/107821622429832886.jpg")
            list.add("http://moimg0.mwim.store/image/5BE8A5CF4893D9197D6D6D66BE294488.jpg")
            list.add("http://cfile.frees.fun/picwall/796804C4A25DE27342A2A0987283AB03.jpg")
            list.add("http://test-yjk.oss-cn-chengdu.aliyuncs.com/APP/dynamic/picture/1637229940713.jpeg")
            list.add("https://gb-small.voopoo.com.cn/voopoo-retail-gb/portrait/202210/OIP-C.jpg")
        }
    }
}
