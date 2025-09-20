package com.lxj.xpopup.animator;

import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromCenter;
import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromLeftBottom;
import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromLeftTop;
import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromRightBottom;
import static com.lxj.xpopup.enums.PopupAnimation.ScaleAlphaFromRightTop;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import com.lxj.xpopup.enums.PopupAnimation;

/**
 * Description: 缩放透明
 * Create by dance, at 2018/12/9
 */
public class ScaleAlphaAnimator extends PopupAnimator {
    public ScaleAlphaAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
    }

    float startScale = .95f;
    @Override
    public void initAnimator() {
        targetView.setScaleX(startScale);
        targetView.setScaleY(startScale);
        targetView.setAlpha(0);

        // 设置动画参考点
        targetView.post(new Runnable() {
            @Override
            public void run() {
                applyPivot();
            }
        });
    }

    /**
     * 根据不同的PopupAnimation来设定对应的pivot
     */
    private void applyPivot() {

        if (popupAnimation == ScaleAlphaFromCenter) {
            targetView.setPivotX(targetView.getMeasuredWidth() / 2f);
            targetView.setPivotY(targetView.getMeasuredHeight() / 2f);

        } else if (popupAnimation == ScaleAlphaFromLeftTop) {
            targetView.setPivotX(0);
            targetView.setPivotY(0);

        } else if (popupAnimation == ScaleAlphaFromRightTop) {
            targetView.setPivotX(targetView.getMeasuredWidth());
            targetView.setPivotY(0f);

        } else if (popupAnimation == ScaleAlphaFromLeftBottom) {
            targetView.setPivotX(0f);
            targetView.setPivotY(targetView.getMeasuredHeight());

        } else if (popupAnimation == ScaleAlphaFromRightBottom) {
            targetView.setPivotX(targetView.getMeasuredWidth());
            targetView.setPivotY(targetView.getMeasuredHeight());
        }

    }

    @Override
    public void animateShow() {
        targetView.post(new Runnable() {
            @Override
            public void run() {
                targetView.animate().scaleX(1f).scaleY(1f).alpha(1f)
                        .setDuration(animationDuration)
                        .setInterpolator(new OvershootInterpolator(1f))
//                .withLayer() 在部分6.0系统会引起crash
                        .start();
            }
        });
    }

    @Override
    public void animateDismiss() {
        if(animating)return;
        observerAnimator(targetView.animate().scaleX(startScale).scaleY(startScale).alpha(0f).setDuration(animationDuration)
                .setInterpolator(new FastOutSlowInInterpolator()))
//                .withLayer() 在部分6.0系统会引起crash
                .start();
    }

}
