package com.lxj.xpopupdemo.fragment

import android.animation.FloatEvaluator
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.AttachPopupView
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.enums.PopupPosition
import com.lxj.xpopup.impl.LoadingPopupView
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.OnInputConfirmListener
import com.lxj.xpopup.interfaces.OnSelectListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopup.util.XPermission
import com.lxj.xpopup.util.XPopupUtils
import com.lxj.xpopupdemo.DemoActivity
import com.lxj.xpopupdemo.MainActivity
import com.lxj.xpopupdemo.R
import com.lxj.xpopupdemo.custom.CustomAttachPopup
import com.lxj.xpopupdemo.custom.CustomAttachPopup2
import com.lxj.xpopupdemo.custom.CustomBubbleAttachPopup
import com.lxj.xpopupdemo.custom.CustomCenter1
import com.lxj.xpopupdemo.custom.CustomDrawerPopupView
import com.lxj.xpopupdemo.custom.CustomEditTextBottomPopup
import com.lxj.xpopupdemo.custom.CustomFullScreenPopup
import com.lxj.xpopupdemo.custom.CustomHorizontalBubbleAttachPopup
import com.lxj.xpopupdemo.custom.ListDrawerPopupView
import com.lxj.xpopupdemo.custom.NotificationMsgPopup
import com.lxj.xpopupdemo.custom.PagerBottomPopup
import com.lxj.xpopupdemo.custom.PagerDrawerPopup
import com.lxj.xpopupdemo.custom.QQMsgPopup
import com.lxj.xpopupdemo.custom.ZhihuCommentPopup

/**
 * Description:
 * Create by lxj, at 2018/12/11
 */
class QuickStartDemo : BaseFragment(), View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.fragment_quickstart

    public override fun init(view: View) {
        view.findViewById<View?>(R.id.tvEditText).requestFocus()
        view.findViewById<View?>(R.id.btnShowConfirm).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnBindLayout).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowPosition1).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowPosition2).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowPosition3).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowInputConfirm).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowCenterList).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowCenterListWithCheck).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowLoading).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowBottomList).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowBottomListWithCheck).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowDrawerLeft).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowDrawerRight).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnCustomBottomPopup).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnPagerBottomPopup).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnCustomEditPopup).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnFullScreenPopup).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnAttachPopup1).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnAttachPopup2).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv1).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv2).setOnClickListener(this)
        view.findViewById<View?>(R.id.tv3).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnMultiPopup).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnShowInBackground).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnBubbleAttachPopup1).setOnClickListener(this)
        view.findViewById<View?>(R.id.btnBubbleAttachPopup2).setOnClickListener(this)
        view.findViewById<View?>(R.id.test).setOnClickListener(this)

        // 必须在事件发生前，调用这个方法来监视View的触摸
        val builder = XPopup.Builder(requireContext()) //                .isCenterHorizontal(true)
            .watchView(view.findViewById<View?>(R.id.btnShowAttachPoint))
        view.findViewById<View?>(R.id.btnShowAttachPoint)
            .setOnLongClickListener(object : OnLongClickListener {
                override fun onLongClick(v: View): Boolean {
                    XPopup.fixLongClick(v) //能保证弹窗弹出后，下层的View无法滑动

                    //                builder.asAttachList(new String[]{"置顶11", "复制", "删除", "编辑编辑编辑编辑"
//                                }, null,
//                                new OnSelectListener() {
//                                    @Override
//                                    public void onSelect(int position, String text) {
//                                        toast("click " + text);
//                                    }
//                                })
//                        .show();
                    builder.isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .atView(v)
                        .isViewMode(true) //                        .offsetY(-XPopupUtils.dp2px(requireContext(), 30))
                        .hasShadowBg(false) // 去掉半透明背景
                        .asCustom(CustomHorizontalBubbleAttachPopup(requireContext()))
                        .show()
                    return true
                }
            })

        drawerPopupView = CustomDrawerPopupView(requireContext())
    }

    var drawerPopupView: CustomDrawerPopupView? = null
    var attachPopupView: AttachPopupView? = null
    var popupView: BasePopupView? = null
    var loadingPopup: LoadingPopupView? = null
    var customAttach2: CustomAttachPopup2? = null

    private fun loopPopup() {
        XPopup.Builder(requireContext())
            .isDestroyOnDismiss(true)
            .asConfirm(
                "哈哈", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                "取消", "确定",
                object : OnConfirmListener {
                    override fun onConfirm() {
                        loopPopup()
                    }
                }, null, false
            )
            .show()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onClick(v: View) {
        val id = v.getId()

        if (id == R.id.test) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .hasStatusBar(false)
                .isRequestFocus(false)
                .asCustom(CustomCenter1(requireContext()))
                .show()
        } else if (id == R.id.btnShowConfirm) {
            popupView = XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .asConfirm(
                    "我是标题", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                    "取消", "确定", object : OnConfirmListener {
                        override fun onConfirm() {}
                    }, null, true
                )
            popupView!!.show()
        } else if (id == R.id.btnBindLayout) {
            XPopup.Builder(requireContext())
                .autoOpenSoftInput(true)
                .isDestroyOnDismiss(true)
                .asInputConfirm(
                    "复用项目已有布局", null,
                    "您可以复用项目已有布局，来使用XPopup强大的交互能力和逻辑封装。",
                    null, object : OnInputConfirmListener {
                        override fun onConfirm(text: String?) {}
                    }, null, R.layout.my_confim_popup
                )
                .show()
        } else if (id == R.id.btnShowInputConfirm) {
            XPopup.Builder(requireContext())
                .hasStatusBarShadow(false)
                .hasNavigationBar(false)
                .isDestroyOnDismiss(true)
                .autoOpenSoftInput(true)
                .isDarkTheme(true)
                .setPopupCallback(DemoXPopupListener())
                .asInputConfirm(
                    "我是标题", "大萨达撒大所大", null, "我是默认Hint文字",
                    object : OnInputConfirmListener {
                        override fun onConfirm(text: String?) {}
                    })
                .show()
        } else if (id == R.id.btnShowCenterList) {
            XPopup.Builder(requireContext())
                .maxHeight(800)
                .isDarkTheme(true)
                .isDestroyOnDismiss(true)
                .asCenterList(
                    "请选择一项",
                    arrayOf<String>("条目1", "条目2", "条目3", "条目4"),
                    object : OnSelectListener {
                        override fun onSelect(position: Int, text: String?) {
                            toast("click " + text)
                        }
                    })
                .show()
        } else if (id == R.id.btnShowCenterListWithCheck) {
            XPopup.Builder(requireContext())
                .asCenterList(
                    "请选择一项", arrayOf<String>("条目1", "条目2", "条目3", "条目4"),
                    null, 1, object : OnSelectListener {
                        override fun onSelect(position: Int, text: String?) {
                            toast("click " + text)
                        }
                    })
                .show()
        } else if (id == R.id.btnShowLoading) {
            if (loadingPopup == null) {
                loadingPopup = XPopup.Builder(requireContext())
                    .dismissOnBackPressed(false)
                    .isLightNavigationBar(true)
                    .asLoading("少时诵诗书", LoadingPopupView.Style.ProgressBar)
                    .show() as LoadingPopupView?
            } else {
                loadingPopup!!.setStyle(LoadingPopupView.Style.ProgressBar)
                loadingPopup!!.show()
            }
            loadingPopup!!.postDelayed(Runnable {
                loadingPopup!!.setTitle("加载中长度变化啊")
                loadingPopup!!.setStyle(LoadingPopupView.Style.Spinner)
                loadingPopup!!.postDelayed(Runnable { loadingPopup!!.setTitle("") }, 2000)
            }, 2000)
            loadingPopup!!.delayDismissWith(6000, Runnable { toast("我消失了！！！") })
        } else if (id == R.id.btnShowBottomList) {
            popupView = XPopup.Builder(requireContext())
                .isDarkTheme(true)
                .hasShadowBg(false)
                .customHostLifecycle(lifecycle)
                .moveUpToKeyboard(false)
                .isDestroyOnDismiss(false)
                .borderRadius(XPopupUtils.dp2px(requireContext(), 15f).toFloat())
                .asBottomList(
                    "请选择一项",
                    arrayOf<String>("条目1", "条目2", "条目3", "条目4", "条目5", "条目6", "条目7"),
                    OnSelectListener { position: Int, text: String? -> toast("click " + text) }) // 注意这里的 text

            popupView!!.show()
        } else if (id == R.id.btnShowBottomListWithCheck) {
            XPopup.Builder(requireContext())
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asBottomList(
                    "标题可以没有",
                    arrayOf<String>("条目1", "条目2", "条目3", "条目4", "条目5"),
                    null, 2,
                    OnSelectListener { position: Int, text: String? -> toast("click " + text) })
                .show()
        } else if (id == R.id.btnCustomBottomPopup) {
            XPopup.Builder(requireContext())
                .hasShadowBg(false)
                .moveUpToKeyboard(false)
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asCustom(ZhihuCommentPopup(requireContext()))
                .show()
        } else if (id == R.id.btnPagerBottomPopup) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .isViewMode(true)
                .asCustom(PagerBottomPopup(requireContext()))
                .show()
        } else if (id == R.id.tv1 || id == R.id.tv2 || id == R.id.tv3) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .hasShadowBg(false)
                .atView(v)
                .asCustom(CustomAttachPopup(requireContext()))
                .show()
        } else if (id == R.id.btnAttachPopup1) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .hasShadowBg(false)
                .atView(v)
                .asCustom(CustomAttachPopup(requireContext()))
                .show()
        } else if (id == R.id.btnAttachPopup2) {
            if (customAttach2 == null) {
                customAttach2 = XPopup.Builder(requireContext())
                    .isDestroyOnDismiss(false)
                    .atView(v)
                    .asCustom(CustomAttachPopup2(requireContext()))
                    .show() as CustomAttachPopup2?
            } else {
                customAttach2!!.show()
            }
        } else if (id == R.id.btnBubbleAttachPopup1) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .atView(v)
                .isViewMode(true)
                .offsetY(XPopupUtils.dp2px(requireContext(), 10f))
                .hasShadowBg(false)
                .asCustom(CustomHorizontalBubbleAttachPopup(requireContext()))
                .show()
        } else if (id == R.id.btnBubbleAttachPopup2) {
            XPopup.Builder(requireContext())
                .hasShadowBg(false)
                .isTouchThrough(true)
                .isDestroyOnDismiss(true)
                .atView(requireView().findViewById(R.id.vv))
                .isCenterHorizontal(true)
                .asCustom(CustomBubbleAttachPopup(requireContext()))
                .show()

            XPopup.Builder(requireContext())
                .isTouchThrough(true)
                .isDestroyOnDismiss(true)
                .atView(requireView().findViewById(R.id.vv2))
                .hasShadowBg(false)
                .asCustom(CustomBubbleAttachPopup(requireContext()))
                .show()
        } else if (id == R.id.btnShowDrawerLeft) {
            XPopup.Builder(requireContext())
                .isViewMode(true)
                .asCustom(PagerDrawerPopup(requireContext()))
                .show()
        } else if (id == R.id.btnShowDrawerRight) {
            popupView = XPopup.Builder(requireContext())
                .autoOpenSoftInput(true)
                .popupPosition(PopupPosition.Right)
                .hasStatusBarShadow(true)
                .setPopupCallback(DemoXPopupListener())
                .asCustom(ListDrawerPopupView(requireContext()))
            popupView!!.show()
        } else if (id == R.id.btnFullScreenPopup) {
            popupView = CustomFullScreenPopup(requireContext())
            XPopup.Builder(requireContext())
                .isLightStatusBar(true)
                .autoOpenSoftInput(true)
                .asCustom(popupView!!)
                .show()
        } else if (id == R.id.btnCustomEditPopup) {
            XPopup.Builder(requireContext())
                .autoOpenSoftInput(true)
                .isDestroyOnDismiss(true)
                .asCustom(CustomEditTextBottomPopup(requireContext()))
                .show()
        } else if (id == R.id.btnShowPosition1) {
            XPopup.Builder(requireContext())
                .offsetY(300)
                .offsetX(-100)
                .hasShadowBg(false)
                .hasBlurBg(true)
                .popupAnimation(PopupAnimation.TranslateFromLeft)
                .asCustom(QQMsgPopup(requireContext()))
                .show()
        } else if (id == R.id.btnShowPosition2) {
            XPopup.Builder(requireContext())
                .hasShadowBg(false)
                .hasBlurBg(true)
                .isDestroyOnDismiss(true)
                .isCenterHorizontal(true)
                .offsetY(200)
                .asCustom(QQMsgPopup(requireContext()))
                .show()
        } else if (id == R.id.btnShowPosition3) {
            XPopup.Builder(requireContext())
                .isDestroyOnDismiss(true)
                .popupAnimation(PopupAnimation.TranslateFromTop)
                .asCustom(NotificationMsgPopup(requireContext()))
                .show()
        } else if (id == R.id.btnMultiPopup) {
            startActivity(Intent(requireContext(), DemoActivity::class.java))
        } else if (id == R.id.btnShowInBackground) {
            XPopup.requestOverlayPermission(requireContext(), object : XPermission.SimpleCallback {
                override fun onGranted() {
                    ToastUtils.showShort("等待2秒后弹出XPopup！！！")
                    ActivityUtils.startHomeActivity()
                    Handler().postDelayed(Runnable {
                        XPopup.Builder(requireContext())
                            .isDestroyOnDismiss(true)
                            .enableShowWhenAppBackground(true)
                            .asConfirm(
                                "XPopup牛逼",
                                "XPopup支持直接在后台弹出！",
                                OnConfirmListener {
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            MainActivity::class.java
                                        )
                                    )
                                }
                            ).show()
                    }, 1000)
                }

                override fun onDenied() {
                    ToastUtils.showShort("权限拒绝需要申请悬浮窗权限！")
                }
            })
        }
    }


    internal class DemoXPopupListener : SimpleCallback() {
        var fEvaluator: FloatEvaluator = FloatEvaluator()
        var iEvaluator: FloatEvaluator = FloatEvaluator()

        override fun onCreated(pv: BasePopupView?) {
            Log.e("tag", "onCreated")
        }

        override fun onShow(popupView: BasePopupView?) {
            Log.e("tag", "onShow")
        }

        override fun onDismiss(popupView: BasePopupView?) {
            Log.e("tag", "onDismiss")
        }

        override fun beforeDismiss(popupView: BasePopupView?) {
            Log.e("tag", "beforeDismiss")
        }

        //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
        override fun onBackPressed(popupView: BasePopupView?): Boolean {
            Log.e("tag", "拦截的返回按键，按返回键XPopup不会关闭了")
            Toast.makeText(
                popupView!!.context,
                "onBackPressed返回true，拦截了返回按键，按返回键XPopup不会关闭了",
                Toast.LENGTH_SHORT
            ).show()
            return true
        }

        override fun onDrag(
            popupView: BasePopupView?,
            value: Int,
            percent: Float,
            upOrLeft: Boolean
        ) {
            super.onDrag(popupView, value, percent, upOrLeft)
            //            Log.e("tag", "value: " + value + "  percent: " + percent);
//            ((Activity) popupView.requireContext()).getWindow().getDecorView().setTranslationX(value);
//            float e = fEvaluator.evaluate(percent, 1.0, 0.8);
//            View decorView = ((Activity) popupView.requireContext()).getWindow().getDecorView();
//            decorView.setScaleX(e);
//            decorView.setScaleY(e);
//            FloatEvaluator iEvaluator = new FloatEvaluator();
//            View decorView = ((Activity) popupView.requireContext()).getWindow().getDecorView();
//            float t = iEvaluator.evaluate(percent, 0, -popupView.getMeasuredWidth()/2);
//            decorView.setTranslationX(t);
        }

        override fun onKeyBoardStateChanged(popupView: BasePopupView?, height: Int) {
            super.onKeyBoardStateChanged(popupView, height)
            Log.e("tag", "onKeyBoardStateChanged height: " + height)
        }

        override fun onClickOutside(popupView: BasePopupView?) {
            super.onClickOutside(popupView)
            Log.e("tag", "onClickOutside")
        }
    }
}
