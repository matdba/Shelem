package com.example.martin.shelem.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;


public class UnitHandler {

    private final static double baseDpi = 428.0;
    private final static double standardScreenHeight = 1920;
    private Activity activity;

    public int screenWidth, screenHeight, navigationBarHeight, baseScreenHeight;
    public double dpi, dpiRatio;

    public UnitHandler(Activity activity) {
        this.activity = activity;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenWidth = displayMetrics.widthPixels;
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId);
            screenHeight = displayMetrics.heightPixels + navigationBarHeight;
        } else {
            screenHeight = displayMetrics.heightPixels;
        }

        dpi = Math.floor(screenWidth * displayMetrics.xdpi / screenWidth);
        dpiRatio = dpi / baseDpi;

        baseScreenHeight = (int) (standardScreenHeight * dpiRatio);
    }


    public void setViewTopMargin (View view, int pixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = pixels;
    }

    public void setViewLeftMargin (View view, int pixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = pixels;
    }

    public void setViewRightMargin (View view, int pixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.rightMargin = pixels;
    }

    public void setViewBottomMargin (View view, int pixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = pixels;
    }


    public void setViewsHeightAndWidth (View view, int xPixels, int yPixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = xPixels;
        layoutParams.height = yPixels;
    }



    public int getPixels(int dp) {
        int t = dp * (int) activity.getResources().getDisplayMetrics().density;
        return (int) (t * this.dpiRatio);
    }
}
