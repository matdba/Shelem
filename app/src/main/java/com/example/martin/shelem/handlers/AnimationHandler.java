package com.example.martin.shelem.handlers;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import com.example.martin.shelem.customViews.ImageViewCustom;

public class AnimationHandler {
    Activity activity;



    public AnimationHandler(Activity activity) {
        this.activity = activity;
    }





    public void topMarginAnimation(final View view, int topMargin, int duration, Interpolator interpolator) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.topMargin, topMargin);
        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.topMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void bottomMarginAnimation(final View view, int bottomMargin, int duration, Interpolator interpolator) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.bottomMargin, bottomMargin);
        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.bottomMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void leftMarginAnimation(final View view, int leftMargin, int duration, Interpolator interpolator) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.leftMargin, leftMargin);
        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.leftMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void rightMarginAnimation(final View view, int rightMargin, int duration, Interpolator interpolator) {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(layoutParams.rightMargin, rightMargin);
        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            layoutParams.rightMargin = (Integer) valueAnimator1.getAnimatedValue();
            view.requestLayout();
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void rotationAnimation(final View view, float rotation, int duration, Interpolator interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(view.getRotation(), rotation);
        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            view.setRotation((float) valueAnimator1.getAnimatedValue());
            view.requestLayout();
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }





    public void scaleAnimation(final View view, float scaleWidth, float scaleHeight, int duration) {
        PropertyValuesHolder newWidth = PropertyValuesHolder.ofFloat("scaleX", scaleWidth);
        PropertyValuesHolder newHeight = PropertyValuesHolder.ofFloat("scaleY", scaleHeight);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, newHeight, newWidth);
        animator.setDuration(duration);
        animator.start();
    }





    public void flipAnimation(final ImageViewCustom view, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(view.getRotationY(), 90);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            view.setRotationY((float) valueAnimator1.getAnimatedValue());
            view.requestLayout();
        });


        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                view.up.setVisibility(View.GONE);
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(270, 360);
                valueAnimator.addUpdateListener(valueAnimator12 -> {
                    view.setRotationY((float) valueAnimator12.getAnimatedValue());
                    view.requestLayout();
                });
                valueAnimator.setDuration(duration);
                valueAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }





    public void flipBackAnimation(final ImageViewCustom view, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(view.getRotationY(), 90);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            view.setRotationY((float) valueAnimator1.getAnimatedValue());
            view.requestLayout();
        });


        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                view.down.setVisibility(View.GONE);
                view.up.setVisibility(View.VISIBLE);
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(270, 360);
                valueAnimator.addUpdateListener(valueAnimator12 -> {
                    view.setRotationY((float) valueAnimator12.getAnimatedValue());
                    view.requestLayout();
                });
                valueAnimator.setDuration(duration);
                valueAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        valueAnimator.setDuration(duration);
        valueAnimator.start();

    }



}
