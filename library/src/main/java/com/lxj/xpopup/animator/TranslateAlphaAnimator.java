package com.lxj.xpopup.animator;

import static com.lxj.xpopup.enums.PopupAnimation.TranslateAlphaFromBottom;
import static com.lxj.xpopup.enums.PopupAnimation.TranslateAlphaFromLeft;
import static com.lxj.xpopup.enums.PopupAnimation.TranslateAlphaFromRight;
import static com.lxj.xpopup.enums.PopupAnimation.TranslateAlphaFromTop;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;

/**
 * Description: 平移动画
 * Create by dance, at 2018/12/9
 */
public class TranslateAlphaAnimator extends PopupAnimator {
    //动画起始坐标
    private float startTranslationX, startTranslationY;
    private float defTranslationX, defTranslationY;
    public TranslateAlphaAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
    }

    @Override
    public void initAnimator() {
        defTranslationX = targetView.getTranslationX();
        defTranslationY = targetView.getTranslationY();

        targetView.setAlpha(0);
        // 设置移动坐标
        applyTranslation();
        startTranslationX = targetView.getTranslationX();
        startTranslationY = targetView.getTranslationY();
    }

    private void applyTranslation() {
        if (popupAnimation == TranslateAlphaFromLeft) {
            targetView.setTranslationX(-targetView.getMeasuredWidth() /* + halfWidthOffset */);
        } else if (popupAnimation == TranslateAlphaFromTop) {
            targetView.setTranslationY(-targetView.getMeasuredHeight() /* + halfHeightOffset */);
        } else if (popupAnimation == TranslateAlphaFromRight) {
            targetView.setTranslationX(targetView.getMeasuredWidth() /* + halfWidthOffset */);
        } else if (popupAnimation == TranslateAlphaFromBottom) {
            targetView.setTranslationY(targetView.getMeasuredHeight() /* + halfHeightOffset */);
        }

    }

    @Override
    public void animateShow() {
        targetView.animate().translationX(defTranslationX).translationY(defTranslationY).alpha(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animationDuration)
                .withLayer()
                .start();
    }

    @Override
    public void animateDismiss() {
        if(animating)return;
        observerAnimator(targetView.animate().translationX(startTranslationX).translationY(startTranslationY).alpha(0f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animationDuration)
                .withLayer())
                .start();
    }
}
