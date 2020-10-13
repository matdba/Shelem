package com.example.martin.playingcards.utils;

import com.example.martin.playingcards.customViews.ImageViewCustom;


public class JavaUtils {

    public static ImageViewCustom[]  concatenateCards(ImageViewCustom[] a, ImageViewCustom[] b, ImageViewCustom[] c, ImageViewCustom[] d, ImageViewCustom[] e) {
        ImageViewCustom[] f = new ImageViewCustom[a.length + b.length + c.length + d.length + e.length];

        for (int i = 0; i < a.length; i++) {
            f[i] = a[i];
        }

        for (int i = a.length; i < a.length + b.length; i++) {
            f[i] = a[i - a.length];
        }

        for (int i = a.length + b.length; i < a.length + b.length + c.length; i++) {
            f[i] = a[i - a.length - b.length];
        }

        for (int i = a.length + b.length + c.length; i < a.length + b.length + c.length + d.length; i++) {
            f[i] = a[i - a.length - b.length - c.length];
        }

        for (int i = a.length + b.length + c.length + d.length; i < a.length + b.length + c.length + d.length + e.length; i++) {
            f[i] = a[i - a.length - b.length - c.length - d.length];
        }

        return f;
    }
}
