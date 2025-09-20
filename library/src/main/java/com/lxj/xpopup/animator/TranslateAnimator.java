package com.lxj.xpopup.animator;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.lxj.xpopup.enums.PopupAnimation;

/**
 * Description: 平移动画，不带渐变
 * Create by dance, at 2018/12/9
 */
public class TranslateAnimator extends PopupAnimator {
    public float startTranslationX, startTranslationY;
    public float endTranslationX, endTranslationY;
    public TranslateAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
    }

    @Override
    public void initAnimator() {
        if(!hasInit){
            endTranslationX = targetView.getTranslationX();
            endTranslationY = targetView.getTranslationY();
            // 设置起始坐标
            applyTranslation();
            startTranslationX = targetView.getTranslationX();
            startTranslationY = targetView.getTranslationY();
        }
    }

    private void applyTranslation() {
        if (popupAnimation == PopupAnimation.TranslateFromLeft) {
            targetView.setTranslationX(-targetView.getRight() + targetView.getTranslationX());
        } else if (popupAnimation == PopupAnimation.TranslateFromTop) {
            targetView.setTranslationY(-targetView.getBottom() + targetView.getTranslationY());
        } else if (popupAnimation == PopupAnimation.TranslateFromRight) {
            targetView.setTranslationX(((View) targetView.getParent()).getMeasuredWidth() - targetView.getLeft() + targetView.getTranslationX());
        } else if (popupAnimation == PopupAnimation.TranslateFromBottom) {
            targetView.setTranslationY(((View) targetView.getParent()).getMeasuredHeight() - targetView.getTop() + targetView.getTranslationY());
        }

    }

    @Override
    public void animateShow() {
        ViewPropertyAnimator animator = null;
        if (popupAnimation == PopupAnimation.TranslateFromLeft || popupAnimation == PopupAnimation.TranslateFromRight) {
            animator = targetView.animate().translationX(endTranslationX);
        } else if (popupAnimation == PopupAnimation.TranslateFromTop || popupAnimation == PopupAnimation.TranslateFromBottom) {
            animator = targetView.animate().translationY(endTranslationY);
        }

        if (animator != null) animator.setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(animationDuration)
                .withLayer()
                .start();
        Log.e("part", "start: " + targetView.getTranslationY() + "  endy: " + endTranslationY);
    }

    @Override
    public void animateDismiss() {
        if (animating) return;
        ViewPropertyAnimator animator = null;
        if (popupAnimation == PopupAnimation.TranslateFromLeft) {
            startTranslationX = -targetView.getRight();
            animator = targetView.animate().translationX(startTranslationX);
        } else if (popupAnimation == PopupAnimation.TranslateFromTop) {
            startTranslationY = -targetView.getBottom();
            animator = targetView.animate().translationY(startTranslationY);
        } else if (popupAnimation == PopupAnimation.TranslateFromRight) {
            startTranslationX = ((View) targetView.getParent()).getMeasuredWidth() - targetView.getLeft();
            animator = targetView.animate().translationX(startTranslationX);
        } else if (popupAnimation == PopupAnimation.TranslateFromBottom) {
            startTranslationY = ((View) targetView.getParent()).getMeasuredHeight() - targetView.getTop();
            animator = targetView.animate().translationY(startTranslationY);
        }

        if (animator != null)
            observerAnimator(animator.setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration((long) (animationDuration * .8))
                    .withLayer())
                    .start();
    }
}
