package com.lxj.xpopupdemo.fragment;

import android.animation.FloatEvaluator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopupdemo.DemoActivity;
import com.lxj.xpopupdemo.MainActivity;
import com.lxj.xpopupdemo.R;
import com.lxj.xpopupdemo.custom.CustomAttachPopup;
import com.lxj.xpopupdemo.custom.CustomAttachPopup2;
import com.lxj.xpopupdemo.custom.CustomBubbleAttachPopup;
import com.lxj.xpopupdemo.custom.CustomCenter1;
import com.lxj.xpopupdemo.custom.CustomDrawerPopupView;
import com.lxj.xpopupdemo.custom.CustomEditTextBottomPopup;
import com.lxj.xpopupdemo.custom.CustomFullScreenPopup;
import com.lxj.xpopupdemo.custom.CustomHorizontalBubbleAttachPopup;
import com.lxj.xpopupdemo.custom.ListDrawerPopupView;
import com.lxj.xpopupdemo.custom.LoginPopup;
import com.lxj.xpopupdemo.custom.NotificationMsgPopup;
import com.lxj.xpopupdemo.custom.PagerBottomPopup;
import com.lxj.xpopupdemo.custom.PagerDrawerPopup;
import com.lxj.xpopupdemo.custom.QQMsgPopup;
import com.lxj.xpopupdemo.custom.ZhihuCommentPopup;

/**
 * Description:
 * Create by lxj, at 2018/12/11
 */
public class QuickStartDemo extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quickstart;
    }

    @Override
    public void init(final View view) {
        view.findViewById(R.id.tvEditText).requestFocus();
        view.findViewById(R.id.btnShowConfirm).setOnClickListener(this);
        view.findViewById(R.id.btnBindLayout).setOnClickListener(this);
        view.findViewById(R.id.btnShowPosition1).setOnClickListener(this);
        view.findViewById(R.id.btnShowPosition2).setOnClickListener(this);
        view.findViewById(R.id.btnShowPosition3).setOnClickListener(this);
        view.findViewById(R.id.btnShowInputConfirm).setOnClickListener(this);
        view.findViewById(R.id.btnShowCenterList).setOnClickListener(this);
        view.findViewById(R.id.btnShowCenterListWithCheck).setOnClickListener(this);
        view.findViewById(R.id.btnShowLoading).setOnClickListener(this);
        view.findViewById(R.id.btnShowBottomList).setOnClickListener(this);
        view.findViewById(R.id.btnShowBottomListWithCheck).setOnClickListener(this);
        view.findViewById(R.id.btnShowDrawerLeft).setOnClickListener(this);
        view.findViewById(R.id.btnShowDrawerRight).setOnClickListener(this);
        view.findViewById(R.id.btnCustomBottomPopup).setOnClickListener(this);
        view.findViewById(R.id.btnPagerBottomPopup).setOnClickListener(this);
        view.findViewById(R.id.btnCustomEditPopup).setOnClickListener(this);
        view.findViewById(R.id.btnFullScreenPopup).setOnClickListener(this);
        view.findViewById(R.id.btnAttachPopup1).setOnClickListener(this);
        view.findViewById(R.id.btnAttachPopup2).setOnClickListener(this);
        view.findViewById(R.id.tv1).setOnClickListener(this);
        view.findViewById(R.id.tv2).setOnClickListener(this);
        view.findViewById(R.id.tv3).setOnClickListener(this);
        view.findViewById(R.id.btnMultiPopup).setOnClickListener(this);
        view.findViewById(R.id.btnShowInBackground).setOnClickListener(this);
        view.findViewById(R.id.btnBubbleAttachPopup1).setOnClickListener(this);
        view.findViewById(R.id.btnBubbleAttachPopup2).setOnClickListener(this);
        view.findViewById(R.id.test).setOnClickListener(this);

        // 必须在事件发生前，调用这个方法来监视View的触摸
        final XPopup.Builder builder = new XPopup.Builder(getContext())
//                .isCenterHorizontal(true)
                .watchView(view.findViewById(R.id.btnShowAttachPoint));
        view.findViewById(R.id.btnShowAttachPoint).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                XPopup.fixLongClick(v);//能保证弹窗弹出后，下层的View无法滑动
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
                        .isViewMode(true)
//                        .offsetY(-XPopupUtils.dp2px(getContext(), 30))
                        .hasShadowBg(false) // 去掉半透明背景
                        .asCustom(new CustomHorizontalBubbleAttachPopup(getContext()))
                        .show();
                return true;
            }
        });

        drawerPopupView = new CustomDrawerPopupView(getContext());
    }

    CustomDrawerPopupView drawerPopupView;
    AttachPopupView attachPopupView;
    BasePopupView popupView;
    LoadingPopupView loadingPopup;
    CustomAttachPopup2 customAttach2;

    private void loopPopup() {
        new XPopup.Builder(getContext())
                .isDestroyOnDismiss(true)
                .asConfirm("哈哈", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                        "取消", "确定",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                loopPopup();
                            }
                        }, null, false)
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.test) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .hasStatusBar(false)
                    .isRequestFocus(false)
                    .asCustom(new CustomCenter1(getContext()))
                    .show();

        } else if (id == R.id.btnShowConfirm) {
            popupView = new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .asConfirm("我是标题", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                            "取消", "确定", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {}
                            }, null, true);
            popupView.show();

        } else if (id == R.id.btnBindLayout) {
            new XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    .isDestroyOnDismiss(true)
                    .asInputConfirm("复用项目已有布局", null,
                            "您可以复用项目已有布局，来使用XPopup强大的交互能力和逻辑封装。",
                            null, new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {}
                            }, null, R.layout.my_confim_popup)
                    .show();

        } else if (id == R.id.btnShowInputConfirm) {
            new XPopup.Builder(getContext())
                    .hasStatusBarShadow(false)
                    .hasNavigationBar(false)
                    .isDestroyOnDismiss(true)
                    .autoOpenSoftInput(true)
                    .isDarkTheme(true)
                    .setPopupCallback(new DemoXPopupListener())
                    .asInputConfirm("我是标题", "大萨达撒大所大", null, "我是默认Hint文字",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {}
                            })
                    .show();

        } else if (id == R.id.btnShowCenterList) {
            new XPopup.Builder(getContext())
                    .maxHeight(800)
                    .isDarkTheme(true)
                    .isDestroyOnDismiss(true)
                    .asCenterList("请选择一项",
                            new String[]{"条目1", "条目2", "条目3", "条目4"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            })
                    .show();

        } else if (id == R.id.btnShowCenterListWithCheck) {
            new XPopup.Builder(getContext())
                    .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4"},
                            null, 1, new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            })
                    .show();

        } else if (id == R.id.btnShowLoading) {
            if (loadingPopup == null) {
                loadingPopup = (LoadingPopupView) new XPopup.Builder(getContext())
                        .dismissOnBackPressed(false)
                        .isLightNavigationBar(true)
                        .asLoading("少时诵诗书", LoadingPopupView.Style.ProgressBar)
                        .show();
            } else {
                loadingPopup.setStyle(LoadingPopupView.Style.ProgressBar);
                loadingPopup.show();
            }
            loadingPopup.postDelayed(() -> {
                loadingPopup.setTitle("加载中长度变化啊");
                loadingPopup.setStyle(LoadingPopupView.Style.Spinner);
                loadingPopup.postDelayed(() -> loadingPopup.setTitle(""), 2000);
            }, 2000);
            loadingPopup.delayDismissWith(6000, () -> toast("我消失了！！！"));

        } else if (id == R.id.btnShowBottomList) {
            popupView = new XPopup.Builder(getContext())
                    .isDarkTheme(true)
                    .hasShadowBg(false)
                    .customHostLifecycle(getLifecycle())
                    .moveUpToKeyboard(false)
                    .isDestroyOnDismiss(false)
                    .borderRadius(XPopupUtils.dp2px(getContext(), 15))
                    .asBottomList("请选择一项",
                            new String[]{"条目1", "条目2", "条目3", "条目4", "条目5", "条目6", "条目7"},
                            (position, text) -> toast("click " + text)); // 注意这里的 text

            popupView.show();

        } else if (id == R.id.btnShowBottomListWithCheck) {
            new XPopup.Builder(getContext())
                    .isViewMode(true)
                    .isDestroyOnDismiss(true)
                    .asBottomList("标题可以没有",
                            new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                            null, 2,
                            (position, text) -> toast("click " + text))
                    .show();

        } else if (id == R.id.btnCustomBottomPopup) {
            new XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .moveUpToKeyboard(false)
                    .isViewMode(true)
                    .isDestroyOnDismiss(true)
                    .asCustom(new ZhihuCommentPopup(getContext()))
                    .show();

        } else if (id == R.id.btnPagerBottomPopup) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .isViewMode(true)
                    .asCustom(new PagerBottomPopup(getContext()))
                    .show();

        } else if (id == R.id.tv1 || id == R.id.tv2 || id == R.id.tv3) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .hasShadowBg(false)
                    .atView(v)
                    .asCustom(new CustomAttachPopup(getContext()))
                    .show();

        } else if (id == R.id.btnAttachPopup1) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .hasShadowBg(false)
                    .atView(v)
                    .asCustom(new CustomAttachPopup(getContext()))
                    .show();

        } else if (id == R.id.btnAttachPopup2) {
            if (customAttach2 == null) {
                customAttach2 = (CustomAttachPopup2) new XPopup.Builder(getContext())
                        .isDestroyOnDismiss(false)
                        .atView(v)
                        .asCustom(new CustomAttachPopup2(getContext()))
                        .show();
            } else {
                customAttach2.show();
            }

        } else if (id == R.id.btnBubbleAttachPopup1) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .atView(v)
                    .isViewMode(true)
                    .offsetY(XPopupUtils.dp2px(getContext(), 10))
                    .hasShadowBg(false)
                    .asCustom(new CustomHorizontalBubbleAttachPopup(getContext()))
                    .show();

        } else if (id == R.id.btnBubbleAttachPopup2) {
            new XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .isTouchThrough(true)
                    .isDestroyOnDismiss(true)
                    .atView(view.findViewById(R.id.vv))
                    .isCenterHorizontal(true)
                    .asCustom(new CustomBubbleAttachPopup(getContext()))
                    .show();

            new XPopup.Builder(getContext())
                    .isTouchThrough(true)
                    .isDestroyOnDismiss(true)
                    .atView(view.findViewById(R.id.vv2))
                    .hasShadowBg(false)
                    .asCustom(new CustomBubbleAttachPopup(getContext()))
                    .show();

        } else if (id == R.id.btnShowDrawerLeft) {
            new XPopup.Builder(getContext())
                    .isViewMode(true)
                    .asCustom(new PagerDrawerPopup(getContext()))
                    .show();

        } else if (id == R.id.btnShowDrawerRight) {
            popupView = new XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    .popupPosition(PopupPosition.Right)
                    .hasStatusBarShadow(true)
                    .setPopupCallback(new DemoXPopupListener())
                    .asCustom(new ListDrawerPopupView(getContext()));
            popupView.show();

        } else if (id == R.id.btnFullScreenPopup) {
            popupView = new CustomFullScreenPopup(getContext());
            new XPopup.Builder(getContext())
                    .isLightStatusBar(true)
                    .autoOpenSoftInput(true)
                    .asCustom(popupView)
                    .show();

        } else if (id == R.id.btnCustomEditPopup) {
            new XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    .isDestroyOnDismiss(true)
                    .asCustom(new CustomEditTextBottomPopup(getContext()))
                    .show();

        } else if (id == R.id.btnShowPosition1) {
            new XPopup.Builder(getContext())
                    .offsetY(300)
                    .offsetX(-100)
                    .hasShadowBg(false)
                    .hasBlurBg(true)
                    .popupAnimation(PopupAnimation.TranslateFromLeft)
                    .asCustom(new QQMsgPopup(getContext()))
                    .show();

        } else if (id == R.id.btnShowPosition2) {
            new XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .hasBlurBg(true)
                    .isDestroyOnDismiss(true)
                    .isCenterHorizontal(true)
                    .offsetY(200)
                    .asCustom(new QQMsgPopup(getContext()))
                    .show();

        } else if (id == R.id.btnShowPosition3) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .popupAnimation(PopupAnimation.TranslateFromTop)
                    .asCustom(new NotificationMsgPopup(getContext()))
                    .show();

        } else if (id == R.id.btnMultiPopup) {
            startActivity(new Intent(getContext(), DemoActivity.class));

        } else if (id == R.id.btnShowInBackground) {
            XPopup.requestOverlayPermission(getContext(), new XPermission.SimpleCallback() {
                @Override
                public void onGranted() {
                    ToastUtils.showShort("等待2秒后弹出XPopup！！！");
                    ActivityUtils.startHomeActivity();
                    new Handler().postDelayed(() -> {
                        new XPopup.Builder(getContext())
                                .isDestroyOnDismiss(true)
                                .enableShowWhenAppBackground(true)
                                .asConfirm("XPopup牛逼", "XPopup支持直接在后台弹出！", () ->
                                        startActivity(new Intent(getContext(), MainActivity.class))
                                ).show();
                    }, 1000);
                }

                @Override
                public void onDenied() {
                    ToastUtils.showShort("权限拒绝需要申请悬浮窗权限！");
                }
            });
        }
    }



    static class DemoXPopupListener extends SimpleCallback {
        FloatEvaluator fEvaluator = new FloatEvaluator();
        FloatEvaluator iEvaluator = new FloatEvaluator();

        @Override
        public void onCreated(BasePopupView pv) {
            Log.e("tag", "onCreated");
        }

        @Override
        public void onShow(BasePopupView popupView) {
            Log.e("tag", "onShow");
        }

        @Override
        public void onDismiss(BasePopupView popupView) {
            Log.e("tag", "onDismiss");
        }

        @Override
        public void beforeDismiss(BasePopupView popupView) {
            Log.e("tag", "beforeDismiss");
        }

        //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
        @Override
        public boolean onBackPressed(BasePopupView popupView) {
            Log.e("tag", "拦截的返回按键，按返回键XPopup不会关闭了");
            Toast.makeText(popupView.getContext(), "onBackPressed返回true，拦截了返回按键，按返回键XPopup不会关闭了", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onDrag(BasePopupView popupView, int value, float percent, boolean upOrLeft) {
            super.onDrag(popupView, value, percent, upOrLeft);
//            Log.e("tag", "value: " + value + "  percent: " + percent);
//            ((Activity) popupView.getContext()).getWindow().getDecorView().setTranslationX(value);
//            float e = fEvaluator.evaluate(percent, 1.0, 0.8);
//            View decorView = ((Activity) popupView.getContext()).getWindow().getDecorView();
//            decorView.setScaleX(e);
//            decorView.setScaleY(e);
//            FloatEvaluator iEvaluator = new FloatEvaluator();
//            View decorView = ((Activity) popupView.getContext()).getWindow().getDecorView();
//            float t = iEvaluator.evaluate(percent, 0, -popupView.getMeasuredWidth()/2);
//            decorView.setTranslationX(t);
        }

        @Override
        public void onKeyBoardStateChanged(BasePopupView popupView, int height) {
            super.onKeyBoardStateChanged(popupView, height);
            Log.e("tag", "onKeyBoardStateChanged height: " + height);
        }

        @Override
        public void onClickOutside(BasePopupView popupView) {
            super.onClickOutside(popupView);
            Log.e("tag", "onClickOutside");
        }
    }
}
