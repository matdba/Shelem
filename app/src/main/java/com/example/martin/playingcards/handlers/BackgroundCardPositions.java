package com.example.martin.playingcards.handlers;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.martin.playingcards.interfaces.BackgroundAnimationListener;

public class BackgroundCardPositions {
    private int x;
    private int y;

    private final int duration = 2000;
    private final double distance = 100;
    private ValueAnimator animatorTOp = null, animatorLeft = null;
    BackgroundAnimationListener backgroundAnimationListener;

    public BackgroundCardPositions(int x, int y, Activity activity) {
        this.x = x;
        this.y = y;
        backgroundAnimationListener = (BackgroundAnimationListener) activity;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    private int calculataDistances(int oldX, int oldY) {
        double distances = Math.sqrt(Math.pow((Math.abs(x - oldX)), 2) + Math.pow(Math.abs(y - oldY), 2));

        return (int) ((distances / distance) * duration);
    }

    public void animateLeft(final View view, final int i, int oldX, int oldY) {


        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        animatorLeft = ValueAnimator.ofInt(params.leftMargin, this.getX());
        animatorLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
                view.requestLayout();
            }
        });
        animatorLeft.setDuration(calculataDistances(oldX, oldY));
        animatorLeft.start();
        animatorLeft.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backgroundAnimationListener.onRespond(i);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public void animateTop(final View view, int oldX, int oldY) {

        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        animatorTOp = ValueAnimator.ofInt(params.topMargin, this.getY());
        animatorTOp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                params.topMargin = (Integer) valueAnimator.getAnimatedValue();
                view.requestLayout();
            }
        });
        animatorTOp.setDuration(calculataDistances(oldX, oldY));
        animatorTOp.start();
        animatorTOp.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }




}
