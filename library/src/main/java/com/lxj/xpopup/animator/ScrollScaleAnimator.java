package com.lxj.xpopup.animator;

import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromBottom;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromLeft;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromLeftBottom;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromLeftTop;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromRight;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromRightBottom;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromRightTop;
import static com.lxj.xpopup.enums.PopupAnimation.ScrollAlphaFromTop;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import com.lxj.xpopup.enums.PopupAnimation;

/**
 * Description: 像系统的PopupMenu那样的动画
 * Create by lxj, at 2018/12/12
 */
public class ScrollScaleAnimator extends PopupAnimator{

    private IntEvaluator intEvaluator = new IntEvaluator();
    private int startScrollX, startScrollY;
    private float startAlpha = 0f;
    private float startScale = 0f;

    public ScrollScaleAnimator(View target, int animationDuration, PopupAnimation popupAnimation) {
        super(target, animationDuration, popupAnimation);
    }

    @Override
    public void initAnimator() {
        targetView.setAlpha(startAlpha);

        targetView.post(new Runnable() {
            @Override
            public void run() {
                // 设置参考点
                applyPivot();
                targetView.scrollTo(startScrollX, startScrollY);
            }
        });
    }

    private void applyPivot(){
        if (popupAnimation == ScrollAlphaFromLeft) {
            targetView.setPivotX(0f);
            targetView.setPivotY(targetView.getMeasuredHeight() / 2f);
            startScrollX = targetView.getMeasuredWidth();
            startScrollY = 0;
            targetView.setScaleX(startScale);

        } else if (popupAnimation == ScrollAlphaFromLeftTop) {
            targetView.setPivotX(0f);
            targetView.setPivotY(0f);
            startScrollX = targetView.getMeasuredWidth();
            startScrollY = targetView.getMeasuredHeight();
            targetView.setScaleX(startScale);
            targetView.setScaleY(startScale);

        } else if (popupAnimation == ScrollAlphaFromTop) {
            targetView.setPivotX(targetView.getMeasuredWidth() / 2f);
            targetView.setPivotY(0f);
            startScrollY = targetView.getMeasuredHeight();
            targetView.setScaleY(startScale);

        } else if (popupAnimation == ScrollAlphaFromRightTop) {
            targetView.setPivotX(targetView.getMeasuredWidth());
            targetView.setPivotY(0f);
            startScrollX = -targetView.getMeasuredWidth();
            startScrollY = targetView.getMeasuredHeight();
            targetView.setScaleX(startScale);
            targetView.setScaleY(startScale);

        } else if (popupAnimation == ScrollAlphaFromRight) {
            targetView.setPivotX(targetView.getMeasuredWidth());
            targetView.setPivotY(targetView.getMeasuredHeight() / 2f);
            startScrollX = -targetView.getMeasuredWidth();
            targetView.setScaleX(startScale);

        } else if (popupAnimation == ScrollAlphaFromRightBottom) {
            targetView.setPivotX(targetView.getMeasuredWidth());
            targetView.setPivotY(targetView.getMeasuredHeight());
            startScrollX = -targetView.getMeasuredWidth();
            startScrollY = -targetView.getMeasuredHeight();
            targetView.setScaleX(startScale);
            targetView.setScaleY(startScale);

        } else if (popupAnimation == ScrollAlphaFromBottom) {
            targetView.setPivotX(targetView.getMeasuredWidth() / 2f);
            targetView.setPivotY(targetView.getMeasuredHeight());
            startScrollY = -targetView.getMeasuredHeight();
            targetView.setScaleY(startScale);

        } else if (popupAnimation == ScrollAlphaFromLeftBottom) {
            targetView.setPivotX(0f);
            targetView.setPivotY(targetView.getMeasuredHeight());
            startScrollX = targetView.getMeasuredWidth();
            startScrollY = -targetView.getMeasuredHeight();
            targetView.setScaleX(startScale);
            targetView.setScaleY(startScale);
        }

    }

    @Override
    public void animateShow() {
        targetView.post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        targetView.setAlpha(fraction);
                        targetView.scrollTo(intEvaluator.evaluate(fraction, startScrollX, 0),
                                intEvaluator.evaluate(fraction, startScrollY, 0));
                        doScaleAnimation(fraction);
                    }
                });
                animator.setDuration(animationDuration).setInterpolator(new FastOutSlowInInterpolator());
                animator.start();
            }
        });

    }

    private void doScaleAnimation(float fraction){
        if (popupAnimation == ScrollAlphaFromLeft || popupAnimation == ScrollAlphaFromRight) {
            targetView.setScaleX(fraction);

        } else if (popupAnimation == ScrollAlphaFromTop || popupAnimation == ScrollAlphaFromBottom) {
            targetView.setScaleY(fraction);

        } else if (popupAnimation == ScrollAlphaFromLeftTop
                || popupAnimation == ScrollAlphaFromLeftBottom
                || popupAnimation == ScrollAlphaFromRightTop
                || popupAnimation == ScrollAlphaFromRightBottom) {
            targetView.setScaleX(fraction);
            targetView.setScaleY(fraction);
        }

    }

    @Override
    public void animateDismiss() {
        if(animating)return;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        observerAnimator(animator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                targetView.setAlpha(1-fraction);
                targetView.scrollTo(intEvaluator.evaluate(fraction, 0, startScrollX),
                        intEvaluator.evaluate(fraction, 0, startScrollY));
                doScaleAnimation(1-fraction);
            }
        });
        animator.setDuration(animationDuration)
                .setInterpolator(new FastOutSlowInInterpolator());
        animator.start();
    }

}
